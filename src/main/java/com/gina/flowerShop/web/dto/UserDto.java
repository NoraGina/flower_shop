package com.gina.flowerShop.web.dto;

import com.gina.flowerShop.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String fullName;

    @NotBlank(message = "UserName is mandatory")
    private String username;

    @NotBlank(message = "Password id mandatory")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(@NotBlank(message = "Name is mandatory") String fullName,
                   @NotBlank(message = "UserName is mandatory") String username,
                   @NotBlank(message = "Password id mandatory") String password,
                   @Email(message = "Email should be valid") String email,
                   @NotBlank(message = "Phone is mandatory") String phone, Set<Role> roles) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                '}';
    }
}
