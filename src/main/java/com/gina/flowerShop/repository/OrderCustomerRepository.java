package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Long> {
}
