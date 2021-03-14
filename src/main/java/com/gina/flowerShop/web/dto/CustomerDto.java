package com.gina.flowerShop.web.dto;

import com.gina.flowerShop.model.OrderCustomer;
import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.model.ShippingAddress;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CustomerDto extends UserDto {
    private Set<ShippingAddress> addressSet;

    private Set<OrderCustomer> orderCustomerSet ;

    public CustomerDto() {
    }

    public CustomerDto(@NotBlank(message = "Name is mandatory") String fullName,
                       @NotBlank(message = "UserName is mandatory") String username,
                       @NotBlank(message = "Password id mandatory") String password,
                       @Email(message = "Email should be valid") String email,
                       @NotBlank(message = "Phone is mandatory") String phone,
                       Set<Role> roles, Set<ShippingAddress> addressSet, Set<OrderCustomer> orderCustomerSet) {
        super(fullName, username, password, email, phone, roles);
        this.addressSet = addressSet;
        this.orderCustomerSet = orderCustomerSet;
    }

    public Set<ShippingAddress> getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set<ShippingAddress> addressSet) {
        this.addressSet = addressSet;
    }

    public Set<OrderCustomer> getOrderCustomerSet() {
        return orderCustomerSet;
    }

    public void setOrderCustomerSet(Set<OrderCustomer> orderCustomerSet) {
        this.orderCustomerSet = orderCustomerSet;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "addressSet=" + addressSet +
                '}';
    }
}
