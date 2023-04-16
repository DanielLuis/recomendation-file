package com.ochre.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(name = "Sale")
public class Sale {
    @JsonProperty(value = "PRODUCT")
    private int productId;
    @JsonProperty(value = "USER")
    private int userId;
}
