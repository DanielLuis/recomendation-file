package com.ochre.controller;

import com.ochre.service.RecommendationSystemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/recommendation-system")
@RestController
@RequiredArgsConstructor
public class RecommendationSystemController implements RecommendationSystemApi {

    private final RecommendationSystemService recommendationSystemService;

    @PostMapping(name = "/process-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> processFile(MultipartFile multipartFile) {
        byte[] jsonBytes = recommendationSystemService.downloadSalesFile(multipartFile).getBytes();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=file.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(jsonBytes.length)
                .body(jsonBytes);
    }
}
