package com.gina.flowerShop.service;

import com.gina.flowerShop.web.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findByUsername(String username);
}
