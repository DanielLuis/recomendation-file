package com.orche.service;

import com.orche.dto.RecommendationReportDto;
import org.springframework.web.multipart.MultipartFile;

public interface RecommendationSystemService {
    RecommendationReportDto processSalesFile(MultipartFile multipartFile);

    String downloadSalesFile(MultipartFile multipartFile);
}
