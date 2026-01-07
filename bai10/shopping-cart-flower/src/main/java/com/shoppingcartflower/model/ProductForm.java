package com.shoppingcartflower.model;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductForm {
    private Long id;
    private int productCode;
    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;

    public ProductForm() {
    }

    public ProductForm(Long id, int productCode, String name, String description, BigDecimal price, MultipartFile image) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
