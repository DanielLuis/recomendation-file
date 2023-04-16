package com.orche.controller;

import com.orche.dto.RecommendationReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "RecommendationSystem", description = "Recommendation System process")
@Validated
public interface RecommendationSystemApi {

    @Operation(summary = "Process Sales Json File")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File Processed",
                    content = @Content(schema = @Schema(implementation = RecommendationReportDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameter", content = @Content),
            @ApiResponse(responseCode = "401", description = "The calling user is not authorized to access this resource", content = @Content),
            @ApiResponse(responseCode = "403", description = "The calling user doesn't have permissions get an process file", content = @Content),
            @ApiResponse(responseCode = "501", description = "Not implemented for source", content = @Content)})
    ResponseEntity<byte[]> processFile(@RequestPart(name = "file") MultipartFile multipartFile);


}

