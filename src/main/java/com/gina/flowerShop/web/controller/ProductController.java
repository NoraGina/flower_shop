package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.Category;
import com.gina.flowerShop.repository.CategoryRepository;
import com.gina.flowerShop.service.ProductService;
import com.gina.flowerShop.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;



    @GetMapping("/provider/add/product/form")
    public String addProductForm(Model model){
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        List<String> origins = new ArrayList<>();
        for(String s : productService.findDistinctOrigin()){
            s = s.replaceAll(",", "");
            origins.add(s);
        }
        model.addAttribute("origins", origins);
        model.addAttribute("product", new ProductDto());
        return "provider-add-product";
    }


    @PostMapping("/provider/add/product")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result, Model model,
                             @RequestParam("imageFile") MultipartFile file, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "/provider/add/product";
        }
        try{
            byte[] byteObjects = convertToBytes(file);
            productDto.setImage(byteObjects);
            if(productService.countByProductNameAndImage(productDto.getProductName(), productDto.getImage())==1l){
                redirectAttributes.addFlashAttribute("message", "Produsul exista deja, poate doriti sa-l editati!?");
                return "redirect:/provider/product/list";
            }
            productService.save(productDto);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ProductDto newProductDto = new ProductDto();
            model.addAttribute("product", newProductDto);
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
            List<String>origins = new ArrayList<>();
            for(String s : productService.findDistinctOrigin()){
                s = s.replaceAll(",", "");
                origins.add(s);
            }
            model.addAttribute("origins",origins);
        }

        return "provider-add-product";
    }

    @GetMapping("/provider/product/list")
    public String displayAllProduct(Model model){

        model.addAttribute("products", productService.findAll());
        List<Category>categoryList = categoryRepository.findByOrderByCategoryNameAsc();
        model.addAttribute("categoryList", categoryList);

        return "provider-product-list";
    }

    @GetMapping("/provider/products/stock")
    public String displayProductStock(Model model, @RequestParam(value = "stock", required = false)int stock){

        model.addAttribute("products", productService.findAllByStockGreaterThan(stock));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        return "provider-products-stock";
    }


    @GetMapping("/provider/products/category")
    public String displayProductsByCategory(Model model, @RequestParam(value = "idCategory" , required = false) Long idCategory){

        model.addAttribute("products",productService.findAllByCategoryId(idCategory));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());

        return "provider-products-category";
    }

    @GetMapping("/provider/products/category/stock")
    public String displayProductsByCategoryAndStock(Model model,
                                                    @RequestParam(value = "idCategory" , required = false) Long idCategory,
                                                    @RequestParam(value = "stock", required = false)int stock){

        model.addAttribute("products",productService.findAllByCategoryIdAndStockGreaterThan(idCategory, stock));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        return "provider-products-category-stock";
    }

    @GetMapping("/provider/update/product/form/{idProduct}")
    public String updateProductForm(@PathVariable("idProduct") Long idProduct, Model model){

        Optional<ProductDto> optionalProductDto = productService.findOne(idProduct);
        if(optionalProductDto.isPresent()){
            final  ProductDto productDto = optionalProductDto.get();
            model.addAttribute("product", productDto);
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        }else{
            new IllegalArgumentException("Invalid id "+idProduct);
        }

        return "provider-update-product";
    }

    @PostMapping("/provider/update/product/{idProduct}")
    public String updateProduct(@PathVariable("idProduct") Long idProduct, @Valid @ModelAttribute ProductDto productDto,
                                BindingResult result, Model model, @RequestParam("imageFile") MultipartFile file,
                                RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "provider-update-product";
        }
        try{
            byte[] byteObjects = convertToBytes(file);
            productDto.setImage(byteObjects);
            redirectAttributes.addFlashAttribute("message", productDto.getProductName()+" a fost editat cu succes");
            productService.save(productDto);
        }catch (Exception e){
            e.printStackTrace();
        }


        return "redirect:/provider/product/list";
    }

    @GetMapping("/provider/delete/product/{idProduct}")
    public String deleteProduct(@PathVariable("idProduct")Long idProduct,  RedirectAttributes redirectAttributes){
        ProductDto productDto = productService.findOne(idProduct).get();
        redirectAttributes.addFlashAttribute("message",productDto.getProductName()+" a fost sters");
        productService.delete(idProduct);

        return "redirect:/provider/product/list";
    }

    private byte[] convertToBytes(MultipartFile file) throws IOException {
        byte[] byteObjects = new byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }
}
