package com.gina.flowerShop.service;

import com.gina.flowerShop.model.OrderCustomer;
import com.gina.flowerShop.repository.OrderCustomerRepository;
import com.gina.flowerShop.web.dto.OrderCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderCustomerServiceImpl implements OrderCustomerService {
    @Autowired
    private OrderCustomerRepository orderCustomerRepository;

    public OrderCustomerDto toDto(OrderCustomer orderCustomer){
        OrderCustomerDto orderCustomerDto = new OrderCustomerDto();
        orderCustomerDto.setIdOrderCustomer(orderCustomer.getIdOrderCustomer());
        orderCustomerDto.setDate(orderCustomer.getDate());
        orderCustomerDto.setTime(orderCustomer.getTime());
        orderCustomerDto.setStatus(orderCustomer.getStatus());
        orderCustomerDto.setCustomer(orderCustomer.getCustomer());
        orderCustomerDto.setShippingAddress(orderCustomer.getShippingAddress());
        orderCustomer.setOrderItemList(orderCustomer.getOrderItemList());

        return orderCustomerDto;
    }

    public OrderCustomer toEntity(OrderCustomerDto orderCustomerDto){
        OrderCustomer orderCustomer = new OrderCustomer();

        if(orderCustomerDto.getIdOrderCustomer()!=null){
            orderCustomer.setIdOrderCustomer(orderCustomerDto.getIdOrderCustomer());
        }
        orderCustomer.setDate(orderCustomerDto.getDate());
        orderCustomer.setTime(orderCustomerDto.getTime());
        orderCustomer.setStatus(orderCustomerDto.getStatus());
        orderCustomer.setShippingAddress(orderCustomerDto.getShippingAddress());
        orderCustomer.setCustomer(orderCustomerDto.getCustomer());
        orderCustomer.setOrderItemList(orderCustomerDto.getOrderItemList());
        return orderCustomer;
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderCustomerDto> findAll() {
        return orderCustomerRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderCustomerDto> findById(Long idOrderCustomer) {
        return orderCustomerRepository.findById(idOrderCustomer).map(this::toDto);
    }

    @Override
    public void save(OrderCustomerDto orderCustomerDto) {
        final OrderCustomer orderCustomer = toEntity(orderCustomerDto);

        orderCustomerRepository.save(orderCustomer);
    }

    @Override
    public void delete(Long idOrderCustomer) {
        orderCustomerRepository.deleteById(idOrderCustomer);
    }
}
