package com.gina.flowerShop.web.dto;

import com.gina.flowerShop.model.Category;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDto {
    private Long idProduct;

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    private byte[] image;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Origin is mandatory")
    private String origin;

    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/, message = "Enter decimal number is mandatory")
    private double price;

    private Set<Category> categories;

    private Integer stock;

    public ProductDto() {
    }

    public ProductDto(@NotBlank(message = "Product name is mandatory") String productName,
                      byte[] image, @NotBlank(message = "Description is mandatory") String description,
                      @NotBlank(message = "Origin is mandatory") String origin,
                      @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/, message = "Enter decimal number is mandatory") double price,
                      Set<Category> categories, Integer stock) {
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.origin = origin;
        this.price = price;
        this.categories = categories;
        this.stock = stock;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageAsString(){
        String base64EncodedImage = Base64.getEncoder().encodeToString(image);
        return base64EncodedImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<String> getCategoryName(){
        return categories.stream().map(Category::getCategoryName).collect(Collectors.toList());
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "idProduct=" + idProduct +
                ", productName='" + productName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", description='" + description + '\'' +
                ", origin=" + origin +
                ", price=" + price +
                ", categories=" + categories +
                ", stock=" + stock +
                '}';
    }
}

