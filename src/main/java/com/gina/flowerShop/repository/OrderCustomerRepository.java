package com.gina.flowerShop.repository;


import com.gina.flowerShop.model.Customer;
import com.gina.flowerShop.model.OrderCustomer;
import com.gina.flowerShop.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Long> {

    //@Query("select id_customer From OrderCustomer o INNER JOIN Customer  where o.idOrderCustomer=:idOrderCustomer")
    //Long findCustomerByIdOrderCustomer(@Param("idOrderCustomer")Long idOrderCustomer);

    @Query("Select o from OrderCustomer o where  o.status=:status")
    List<OrderCustomer> findAllByStatus( @Param("status") Status status);

}
