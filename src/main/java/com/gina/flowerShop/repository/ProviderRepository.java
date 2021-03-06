package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Provider findByUsername(String username);

    @Query("select count(*) from Provider u where u.username =:username")
    long countByUsername(@Param("username") String username);
}
