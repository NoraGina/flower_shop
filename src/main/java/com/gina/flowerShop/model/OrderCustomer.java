package com.gina.flowerShop.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders_customer")
public class OrderCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_customer")
    private Long idOrderCustomer;

    @Column(name = "date")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name="suggestion")
    private String suggestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    @NotFound(action = NotFoundAction.IGNORE)
    private Customer customer;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "OrderCustomer_ShippingAddress",
            joinColumns =
                    { @JoinColumn(name = "order_customer_id", referencedColumnName = "id_order_customer") },
            inverseJoinColumns =
                    { @JoinColumn(name = "shipping_address_id", referencedColumnName = "id_shipping_address") })
    private ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "orderCustomer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;


    @Enumerated(EnumType.STRING)
    @Column(name = "status_type", length = 9)
    private Status status;

    public OrderCustomer() {
    }

    public OrderCustomer(Long idOrderCustomer, LocalDate date, LocalTime time, Customer customer,
                         ShippingAddress shippingAddress, List<OrderItem> orderItemList, Status status) {
        this.idOrderCustomer = idOrderCustomer;
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.orderItemList = orderItemList;
        this.status = status;
    }

    public OrderCustomer(Long idOrderCustomer, LocalDate date, LocalTime time, Customer customer,
                         ShippingAddress shippingAddress, Status status) {
        this.idOrderCustomer = idOrderCustomer;
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.status = status;
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

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public double getTotal(){
        double total = 0;
        for(OrderItem orderItem: orderItemList){
            total = orderItem.getQuantity()* orderItem.getProduct().getPrice();
        }
        return total;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCustomer)) return false;
        OrderCustomer that = (OrderCustomer) o;
        if(idOrderCustomer != null){
            return idOrderCustomer.equals(that.idOrderCustomer) &&
                    date.equals(that.date);
        }else{
            return  date.equals(that.date);
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrderCustomer, date);
    }

    @Override
    public String toString() {
        return "OrderCustomer{" +
                "idOrderCustomer=" + idOrderCustomer +
                ", date=" + date +
                ", time=" + time +
                ", suggestion='" + suggestion + '\'' +
                ", customer=" + customer +
                ", shippingAddress=" + shippingAddress +
                ", orderItemList=" + orderItemList +
                ", status=" + status +
                '}';
    }
}
