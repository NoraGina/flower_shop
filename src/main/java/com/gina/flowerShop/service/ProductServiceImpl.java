package com.gina.flowerShop.service;

import com.gina.flowerShop.model.Product;
import com.gina.flowerShop.repository.ProductRepository;
import com.gina.flowerShop.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    private ProductDto toDTO(Product product) {
        final ProductDto productDto = new ProductDto();
        productDto.setIdProduct(product.getIdProduct());
        productDto.setProductName(product.getProductName());
        productDto.setImage(product.getImage());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setOrigin(product.getOrigin());
        productDto.setCategories(product.getCategories());
        productDto.setStock(product.getStock());

        return productDto;
    }

    private Product toEntity(ProductDto productDto) {
        final Product product = new Product();
        if (productDto.getIdProduct() != null) {
            product.setIdProduct(productDto.getIdProduct());
        }
        product.setIdProduct(productDto.getIdProduct());
        product.setProductName(productDto.getProductName());
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setOrigin(productDto.getOrigin());
        product.setCategories(productDto.getCategories());
        product.setStock(productDto.getStock());

        return product;
    }

    @Override
    public void save(ProductDto productDto) {
        final Product product = toEntity(productDto);

        productRepository.save(product);
    }

    @Override
    public void delete(Long idProduct) {
        productRepository.deleteById(idProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<Product> productModelList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productModelList) {
            ProductDto productDto = toDTO(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> findOne(Long idProduct) {
        return productRepository.findById(idProduct).map(this::toDTO);
    }

    @Override
    public List<String> findDistinctOrigin() {
        List<String>originList = productRepository.findDistinctOrigin();
        List<String>origins = new ArrayList<>();
        for(String origin:originList){
            origins.add(origin);
        }
        return originList;
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAllByCategoryId(Long idCategory) {
        List<Product> productModelList = productRepository.findAllByCategoryId(idCategory);
        List<ProductDto>productDtoList = new ArrayList<>();
        for(Product product:productModelList){
            ProductDto productDto = toDTO(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAllByStockGreaterThan(int stock) {
        List<Product> productModelList = productRepository.findAllByStockGreaterThan(stock);
        List<ProductDto>productDtoList = new ArrayList<>();
        for(Product product:productModelList){
            ProductDto productDto = toDTO(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAllByCategoryIdAndStockGreaterThan(Long idCategory, int stock) {
        List<Product> productModelList = productRepository.findAllByCategoryIdAndStockGreaterThan(idCategory, stock);
        List<ProductDto>productDtoList = new ArrayList<>();
        for(Product product:productModelList){
            ProductDto productDto = toDTO(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
