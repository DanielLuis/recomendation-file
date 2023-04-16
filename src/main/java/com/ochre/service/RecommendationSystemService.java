package com.ochre.service;

import com.ochre.dto.RecommendationReportDto;
import org.springframework.web.multipart.MultipartFile;

public interface RecommendationSystemService {
    RecommendationReportDto processSalesFile(MultipartFile multipartFile);

    String downloadSalesFile(MultipartFile multipartFile);
}
