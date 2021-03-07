package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("Select i From OrderItem i where orderCustomer.idOrderCustomer=:idOrderCustomer")
    List<OrderItem> findAllByIdOrderCustomer(@Param("idOrderCustomer") Long idOrderCustomer);
}
