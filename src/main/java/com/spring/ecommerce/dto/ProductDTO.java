package com.spring.ecommerce.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private double weight;
    @Column(length = 1023)
    private String description;
    private String image;
    private int categoryId;
}
