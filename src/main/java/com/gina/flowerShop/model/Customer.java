package com.gina.flowerShop.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends User {
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<ShippingAddress> shippingAddresses;

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<OrderCustomer> orderCustomerSet;*/
    public Customer() {
    }

    public Customer(Long id, String fullName, String username, @Email String email, String phone, String password,
                    Set<Role> roles, Set<ShippingAddress> shippingAddresses) {
        super(id, fullName, username, email, phone, password, roles);
        this.shippingAddresses = shippingAddresses;
    }

    public Set<ShippingAddress> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(Set<ShippingAddress> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "shippingAddresses=" + shippingAddresses +
                '}';
    }
}
