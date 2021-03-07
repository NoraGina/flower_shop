package com.gina.flowerShop.service;

import com.gina.flowerShop.web.dto.CustomerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface CustomerService extends UserDetailsService {
    List<CustomerDto> findAll();
    void save(CustomerDto customerDto);
    Optional<CustomerDto> findById(Long id);
    void delete(Long id);
    long countByUserName( String username);
    CustomerDto findByUsername(String username);
}
