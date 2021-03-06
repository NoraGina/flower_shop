package com.gina.flowerShop.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "providers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Provider extends User{
    public Provider() {
    }

    public Provider(Long id, String fullName, String username, @Email String email,
                    String phone, String password, Set<Role> roles) {
        super(id, fullName, username, email, phone, password, roles);
    }

    @Override
    public String toString() {
        return "Provider{} " + super.toString();
    }
}
