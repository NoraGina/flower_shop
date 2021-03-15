package com.gina.flowerShop.repository;


import com.gina.flowerShop.model.Customer;
import com.gina.flowerShop.model.OrderCustomer;
import com.gina.flowerShop.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Long> {

    @Query("Select o from OrderCustomer o where  o.status=:status")
    List<OrderCustomer> findAllByStatus( @Param("status") Status status);

    @Query("Select o from OrderCustomer o where  o.date=:date")
    List<OrderCustomer> findAllByDate(@Param("date")LocalDate date);

}
