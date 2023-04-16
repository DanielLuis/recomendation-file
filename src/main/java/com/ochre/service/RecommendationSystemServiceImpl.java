package com.ochre.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ochre.dto.RecommendationReportDto;
import com.ochre.model.ProductInformation;
import com.ochre.model.Sale;
import com.ochre.model.SaleInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ochre.config.CachingConfig.PRODUCT_CUSTOMERS;
import static com.ochre.config.CachingConfig.PRODUCT_QUANTITY;
import static com.ochre.config.CachingConfig.RECOMMENDATION_REPORT;
import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationSystemServiceImpl implements RecommendationSystemService {

    @Override
    @Cacheable(RECOMMENDATION_REPORT)
    public RecommendationReportDto processSalesFile(MultipartFile multipartFile) {
        Instant start = Instant.now();

        SaleInformation saleInformation = fileToObject(multipartFile);

        Map<Integer, Set<Integer>> productCustomers = productCustomers(saleInformation);

        Set<String> productQuantity = getProductQuantity(productCustomers);

        List<ProductInformation> productInformation = getProductInformation(productCustomers);

        Instant finish = Instant.now();

        return RecommendationReportDto.builder()
                .productQuantityReport(productQuantity)
                .productInformations(productInformation)
                .time(Duration.between(start, finish).toSeconds())
                .build();
    }

    @Override
    public String downloadSalesFile(MultipartFile multipartFile) {
        RecommendationReportDto recommendationReportDto = processSalesFile(multipartFile);
        return objectToString(recommendationReportDto);
    }

    @Cacheable(PRODUCT_CUSTOMERS)
    private Map<Integer, Set<Integer>> productCustomers(SaleInformation saleInformation) {
        return saleInformation.getSales().stream()
                .collect(groupingBy(Sale::getProductId, mapping(Sale::getUserId, Collectors.toSet())));
    }

    @Cacheable(PRODUCT_QUANTITY)
    private Set<String> getProductQuantity(Map<Integer, Set<Integer>> productCustomers) {
        return productCustomers.entrySet()
                .stream()
                .map(this::createMessage)
                .collect(Collectors.toSet());
    }

    private String createMessage(Map.Entry<Integer, Set<Integer>> p) {
        return " product" + p.getKey() + " was bought by " + p.getValue().size();
    }

    private SaleInformation fileToObject(MultipartFile multipartFile) {
        try {
            return new ObjectMapper().readValue(multipartFile.getInputStream(), SaleInformation.class);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String objectToString(RecommendationReportDto recommendationReportDto) {
        try {
            return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(recommendationReportDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductInformation> getProductInformation(Map<Integer, Set<Integer>> productCustomers) {
        Comparator<ProductInformation> comparator = Comparator.comparingDouble(ProductInformation::getM1).reversed()
                .thenComparingInt(ProductInformation::getProductId);

        return productCustomers.keySet().parallelStream()
                .map(product -> getRelatedProducts(productCustomers, product))
                .flatMap(Collection::stream)
                .sorted(comparator)
                .toList();
    }

    private Set<ProductInformation> getRelatedProducts(Map<Integer, Set<Integer>> productCustomers, Integer product) {
        Map<Integer, Integer> quantity = new HashMap<>();

        productCustomers.keySet().stream()
                .filter(not(isEqual(product)))
                .forEach(otherProduct -> {
                    Set<Integer> commonCustomers = new HashSet<>(productCustomers.get(product));
                    commonCustomers.retainAll(productCustomers.get(otherProduct));
                    if (commonCustomers.size() > 0) {
                        quantity.put(otherProduct, commonCustomers.size());
                    }
                });


        return quantity.entrySet().stream()
                .map(entry -> {
                    int totalCustomers = productCustomers.get(product).size();
                    return ProductInformation.builder()
                            .productId(product)
                            .relatedProductId(entry.getKey())
                            .quantity(entry.getValue())
                            .m1((double) entry.getValue() / totalCustomers)
                            .build();
                })
                .collect(Collectors.toSet());
    }

}
