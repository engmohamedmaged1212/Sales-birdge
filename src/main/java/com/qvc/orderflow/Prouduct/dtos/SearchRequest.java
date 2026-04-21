package com.qvc.orderflow.Prouduct.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class SearchRequest {
    Long productId;
    Map<String, String> filters;
}
