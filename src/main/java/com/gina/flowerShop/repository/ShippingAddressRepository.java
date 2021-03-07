package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    @Query("SELECT DISTINCT s FROM ShippingAddress s left join fetch s.customer where customer_id=:id")
    List<ShippingAddress> findAllByCustomerId(@Param("id")Long id);

    @Query("select count(*) from ShippingAddress s where s.name =:name")
    long countByName(@Param("name") String name);
}
