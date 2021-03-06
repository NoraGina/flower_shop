package com.gina.flowerShop.web;

import com.gina.flowerShop.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProviderDto extends UserDto{
    public ProviderDto() {
        super();
    }

    public ProviderDto(@NotBlank(message = "Name is mandatory") String fullName,
                       @NotBlank(message = "UserName is mandatory") String username,
                       @NotBlank(message = "Password id mandatory") String password,
                       @Email(message = "Email should be valid") String email,
                       @NotBlank(message = "Phone is mandatory") String phone, Set<Role> roles) {
        super(fullName, username, password, email, phone, roles);
    }


    public List<String> getRoleName(){
        //List<String>roleNames = new ArrayList<>();
        //for(Role role:getRoles())
        return getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ProviderDto{} " + super.toString();
    }
}
