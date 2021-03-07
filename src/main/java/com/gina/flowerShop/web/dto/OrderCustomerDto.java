package com.gina.flowerShop.web.dto;

import com.gina.flowerShop.model.Customer;
import com.gina.flowerShop.model.OrderItem;
import com.gina.flowerShop.model.ShippingAddress;
import com.gina.flowerShop.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderCustomerDto {
    private Long idOrderCustomer;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    private Customer customer;

    private ShippingAddress shippingAddress;

    private Status status;

    private List<OrderItem> orderItemList;

    public OrderCustomerDto() {
    }

    public OrderCustomerDto(LocalDate date, LocalTime time, Customer customer,
                            ShippingAddress shippingAddress, Status status, List<OrderItem> orderItemList) {
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.orderItemList = orderItemList;
    }



    public Long getIdOrderCustomer() {
        return idOrderCustomer;
    }

    public void setIdOrderCustomer(Long idOrderCustomer) {
        this.idOrderCustomer = idOrderCustomer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }


    @Override
    public String toString() {
        return "OrderCustomerDto{" +
                "idOrderCustomer=" + idOrderCustomer +
                ", date=" + date +
                ", time=" + time +
                ", customer=" + customer +
                ", shippingAddress=" + shippingAddress +
                ", status=" + status +
                ", orderItemDtoList=" + orderItemList +
                '}';
    }
}
