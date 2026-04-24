package com.qvc.orderflow.Prouduct.Main_Products.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class SearchRequest {
    Long productId;
    Map<String, String> filters;
}
