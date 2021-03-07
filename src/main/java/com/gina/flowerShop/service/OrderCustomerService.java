package com.gina.flowerShop.service;

import com.gina.flowerShop.web.dto.OrderCustomerDto;

import java.util.List;
import java.util.Optional;

public interface OrderCustomerService {
    List<OrderCustomerDto> findAll();
    Optional<OrderCustomerDto> findById(Long idOrderCustomer);
    void save(OrderCustomerDto orderCustomerDto);
    void delete(Long idOrderCustomer);
}
