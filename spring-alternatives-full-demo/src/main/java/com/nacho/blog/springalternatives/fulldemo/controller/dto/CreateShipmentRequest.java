package com.nacho.blog.springalternatives.fulldemo.controller.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateShipmentRequest {

    private Integer userId;
    private List<ItemRequest> items;

    @Data
    @Builder
    public static class ItemRequest {
        private UUID product;
        private Integer quantity;
    }
}
