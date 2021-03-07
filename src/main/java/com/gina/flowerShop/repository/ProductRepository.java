package com.gina.flowerShop.repository;

import com.gina.flowerShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p.origin FROM Product p")
    List<String> findDistinctOrigin();

    @Query("select p from Product p  INNER JOIN p.categories c where c.idCategory=:idCategory")
    List<Product> findAllByCategoryId(@Param("idCategory")Long idCategory);

    @Query("Select p from Product p where p.stock=:stock")
    List<Product>findAllByStockGreaterThan(@Param("stock") int stock);

    @Query("select p from Product p  INNER JOIN p.categories c where c.idCategory=:idCategory and p.stock=:stock")
    List<Product> findAllByCategoryIdAndStockGreaterThan(@Param("idCategory")Long idCategory, @Param("stock")int stock);

    Product findProductByIdProduct(Long idProduct);
}
