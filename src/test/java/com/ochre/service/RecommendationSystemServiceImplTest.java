package com.ochre.service;

import com.ochre.dto.RecommendationReportDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RecommendationSystemServiceImplTest {

    public static final String JSON_SALES_REPORT_COMPRESSED_JSON = "json/sales-report-compressed.json";
    public static final String JSON_SALES_REPORT_JSON = "json/sales-report.json";
    @Autowired
    private RecommendationSystemService recommendationSystemService;

    private static Stream<Arguments> processSalesFile() {
        return Stream.of(
                Arguments.of(JSON_SALES_REPORT_COMPRESSED_JSON, 7, 4, 8),
                Arguments.of(JSON_SALES_REPORT_JSON, 44398, 10850, 2837100)
        );
    }

    @ParameterizedTest
    @MethodSource
    void processSalesFile(String jsonPath, int saleSize, int productReportSize, int productInformation) throws IOException {
        MockMultipartFile file = getJsonToMultiPartFile(jsonPath);

        RecommendationReportDto recommendationReportDto = recommendationSystemService.processSalesFile(file);

        assertThat(recommendationReportDto).isNotNull();
        assertThat(recommendationReportDto.getProductQuantityReport()).isNotNull();
        assertThat(recommendationReportDto.getProductQuantityReport()).hasSize(productReportSize);
        assertThat(recommendationReportDto.getProductInformations()).isNotNull();
        assertThat(recommendationReportDto.getProductInformations()).hasSize(productInformation);
        assertThat(recommendationReportDto.getTime()).isLessThan(15);

    }

    private MockMultipartFile getJsonToMultiPartFile(String jsonPath) throws IOException {
        // Load File
        File file = new File(this.getClass().getClassLoader().getResource(jsonPath).getFile());
        // Convert to Multipart mock
        return new MockMultipartFile(file.getName(), file.getName(), "application/json", new FileInputStream(file));
    }

}