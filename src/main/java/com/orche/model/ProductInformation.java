package com.orche.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductInformation")
public class ProductInformation {
    private int productId;
    private int relatedProductId;
    private int quantity;
    private double m1;
}
