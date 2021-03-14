package com.gina.flowerShop.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends User {
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<ShippingAddress> shippingAddresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderCustomer> orderCustomerSet;

    public Customer() {
    }

    public Customer(Long id, String fullName, String username, @Email String email, String phone, String password,
                    Set<Role> roles, Set<ShippingAddress> shippingAddresses, Set<OrderCustomer> orderCustomerSet) {
        super(id, fullName, username, email, phone, password, roles);
        this.shippingAddresses = shippingAddresses;
        this.orderCustomerSet = orderCustomerSet;
    }

    public Customer(String fullName, String username, String password, Set<Role> roles,
                    Set<ShippingAddress> shippingAddresses, Set<OrderCustomer> orderCustomerSet) {
        super(fullName, username, password, roles);
        this.shippingAddresses = shippingAddresses;
        this.orderCustomerSet = orderCustomerSet;
    }

    public Set<ShippingAddress> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(Set<ShippingAddress> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public Set<OrderCustomer> getOrderCustomerSet() {
        return orderCustomerSet;
    }

    public void setOrderCustomerSet(Set<OrderCustomer> orderCustomerSet) {
        this.orderCustomerSet = orderCustomerSet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "shippingAddresses=" + shippingAddresses +
                '}';
    }
}
