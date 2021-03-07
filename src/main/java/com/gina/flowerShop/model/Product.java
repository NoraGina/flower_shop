package com.gina.flowerShop.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;


    @Column(name = "origin")
    private String origin;

    @Lob
    @Column(length = 100000, name = "image")
    private byte[] image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "idProduct"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "idCategory"))
    private Set<Category> categories = new HashSet<>();

    @Column(name = "stock")
    private Integer stock;

   /* @OneToMany(mappedBy = "product", cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;*/

    public Product() {
    }



    public Product(Long idProduct, String productName, String description,
                   double price, String origin, byte[] image, Set<Category> categories,
                   Integer stock) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageAsString(){
        String base64EncodedImage = Base64.getEncoder().encodeToString(image);
        return base64EncodedImage;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

   /* public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        if(idProduct != null){
            return idProduct.equals(product.idProduct) &&
                    productName.equals(product.productName);
        }
        return   productName.equals(product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, productName);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", origin=" + origin +
                ", image=" + Arrays.toString(image) +
                ", categories=" + categories +
                ", stock=" + stock +
                '}';
    }
}
