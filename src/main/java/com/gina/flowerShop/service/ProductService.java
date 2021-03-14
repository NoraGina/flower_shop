package com.gina.flowerShop.service;

import com.gina.flowerShop.web.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();
    Optional<ProductDto> findOne(Long idProduct);
    void save(ProductDto productDto);
    void delete(Long idProduct);
    List<String>findDistinctOrigin();
    List<ProductDto> findAllByCategoryId(Long idCategory);
    List<ProductDto>findAllByStockGreaterThan( int stock);
    List<ProductDto> findAllByCategoryIdAndStockGreaterThan(Long idCategory, int stock);
    List<ProductDto>findAllByStockGreaterThanAndOrigin( int stock, String origin);
    List<ProductDto> findAllByCategoryIdOriginAndStockGreaterThan(Long idCategory, String origin, Integer stock);
    long countByProductNameAndImage(String productName, byte[] image);

}
