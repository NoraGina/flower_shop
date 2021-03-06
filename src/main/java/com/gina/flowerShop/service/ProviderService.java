package com.gina.flowerShop.service;

import com.gina.flowerShop.web.dto.ProviderDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface ProviderService extends UserDetailsService {
    ProviderDto findByUsername(String username);
    List<ProviderDto> findAll();
    void save(ProviderDto providerDto);
    Optional<ProviderDto> findById(Long idUser);
    void delete(Long idUser);
    long countByUserName( String username);
}
