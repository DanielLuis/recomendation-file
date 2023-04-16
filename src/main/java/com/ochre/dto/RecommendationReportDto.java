package com.ochre.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ochre.model.ProductInformation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Schema(name = "RecommendationReport")
public class RecommendationReportDto {

    @Schema(description = " A structure that will contain the amount of customers that have bought a specific", example = "3 Customers bought this product 1")
    Set<String> productQuantityReport;

    @Schema(description = "A structure that will associate every product to each other (N*N), every association will contain the following info")
    List<ProductInformation> productInformations;

    @Schema(description = "execution time", example = "3")
    @JsonIgnore
    long time;
}
