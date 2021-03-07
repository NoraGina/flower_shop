package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);

    @Query("select count(*) from Customer c where c.username =:username")
    long countByUsername(@Param("username") String username);
}
