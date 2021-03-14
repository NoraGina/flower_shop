package com.gina.flowerShop.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shipping_addresses")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shipping_address")
    private Long idShippingAddress;

    @Column(name = "name", nullable = false)
    private String name;

    private String city;

    private String address;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Customer customer;

    @OneToOne(mappedBy = "shippingAddress",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private OrderCustomer orderCustomer;

    public ShippingAddress() {
    }

    public ShippingAddress(Long idShippingAddress, String name, String city,
                           String address, String phone, Customer customer) {
        this.idShippingAddress = idShippingAddress;
        this.name = name;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.customer = customer;
    }

    public Long getIdShippingAddress() {
        return idShippingAddress;
    }

    public void setIdShippingAddress(Long idShippingAddress) {
        this.idShippingAddress = idShippingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderCustomer getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(OrderCustomer orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShippingAddress)) return false;
        ShippingAddress that = (ShippingAddress) o;
        if(idShippingAddress != null){
            return idShippingAddress.equals(that.idShippingAddress) &&
                    name.equals(that.name);
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idShippingAddress, name);
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
                "idShippingAddress=" + idShippingAddress +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
