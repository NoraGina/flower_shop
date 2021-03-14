package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);

    @Query("select c from Customer c INNER JOIN c.roles r where c.email=:email and r.name=:name")
    Customer findByEmailAndRoleName(@Param("email")String email,@Param("name") String name);
    @Query("select count(*) from Customer c where c.username =:username")
    long countByUsername(@Param("username") String username);


}
