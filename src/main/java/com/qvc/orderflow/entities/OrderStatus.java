package com.qvc.orderflow.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public enum OrderStatus {
    new_, processing, shipped, cancelled;

    @Override public String toString() {
        return name().equals("new_") ? "new" : name();
    }

    @Converter(autoApply = true)
    public static class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
        @Override public String convertToDatabaseColumn(OrderStatus s) {
            return s == null ? null : s.toString();
        }
        @Override public OrderStatus convertToEntityAttribute(String s) {
            if (s == null) return null;
            for (OrderStatus v : values()) if (v.toString().equals(s)) return v;
            throw new IllegalArgumentException("Unknown status: " + s);
        }
    }
}