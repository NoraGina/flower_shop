package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.Category;
import com.gina.flowerShop.repository.CategoryRepository;
import com.gina.flowerShop.repository.ProductRepository;
import com.gina.flowerShop.service.CustomerService;
import com.gina.flowerShop.service.ProductService;
import com.gina.flowerShop.service.UserService;
import com.gina.flowerShop.web.dto.CustomerDto;
import com.gina.flowerShop.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired UserService userService;
    @Autowired ProductService productService;
    @Autowired CategoryRepository categoryRepository;
    @Autowired CustomerService customerService;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails currentUser){
        if(currentUser != null){
            CustomerDto customerDto = customerService.findByUsername(currentUser.getUsername());
            model.addAttribute("customer", customerDto);
            model.addAttribute("products", productService.findAll());
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
            model.addAttribute("origins",productService.findDistinctOrigin());
            return "index";
        }

        model.addAttribute("products", productService.findAll());
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        model.addAttribute("origins",productService.findDistinctOrigin());
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails currentUser, Model model ){
        // SecurityContext context = SecurityContextHolder.getContext();

        // model.addAttribute("message", "You are logged in as "
        //         + context.getAuthentication().getName());
        UserDto user= userService.findByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", user);

        return "dashboard";
    }

    /*@GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response,  @AuthenticationPrincipal UserDetails currentUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            UserDto user= userService.findByUsername(currentUser.getUsername());

            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/?logout"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }*/

    @GetMapping("/available/products")
    public String getAvailableProducts(Model model){

        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        model.addAttribute("origins",productService.findDistinctOrigin());
        model.addAttribute("products", productService.findAllByStockGreaterThan(1));

        return "available-products";
    }

    @GetMapping("/customer/byPage")
    public String byPage(Model model, @AuthenticationPrincipal UserDetails currentUser){
        CustomerDto customerDto = customerService.findByUsername(currentUser.getUsername());
        model.addAttribute("customer", customerDto);
        model.addAttribute("products", productService.findAllByStockGreaterThan(1));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
       /* List<String> origins = new ArrayList<>();
        for(String s:productService.findDistinctOrigin()){
            s=s.replaceAll(",","");
            origins.add(s);
        }*/
        model.addAttribute("origins",productService.findDistinctOrigin());
        return "customer-byPage";
    }

    @GetMapping("/customer/available/category")
    public String getAllAvailableProductsByCategory(Model model, @AuthenticationPrincipal UserDetails currentUser,
                                                    @RequestParam(value = "idCategory" , required = false) Long idCategory){
        if(currentUser != null){
            CustomerDto customerDto = customerService.findByUsername(currentUser.getUsername());
            model.addAttribute("customer", customerDto);
            Category category = categoryRepository.getOne(idCategory);
            model.addAttribute("category", category);
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
            model.addAttribute("origins",productService.findDistinctOrigin());
            model.addAttribute("products",productService.findAllByCategoryIdAndStockGreaterThan(idCategory, 1));
            return "customer-available-category";
        }

        Category category = categoryRepository.getOne(idCategory);
        model.addAttribute("category", category);
        model.addAttribute("products",productService.findAllByCategoryIdAndStockGreaterThan(idCategory, 1));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        model.addAttribute("origins",productService.findDistinctOrigin());
        return "customer-available-category";

    }

    @GetMapping("/customer/available/origin")
    public String getAllAvailableProductsByOrigin(Model model, @AuthenticationPrincipal UserDetails currentUser,
                                                  @RequestParam(value= "origin", required = false)String origin){
        if(currentUser != null){
            CustomerDto customerDto = customerService.findByUsername(currentUser.getUsername());
            model.addAttribute("customer", customerDto);
            String replaceString = origin.replaceAll(",", "");
            String trimString = replaceString;
            model.addAttribute("origin", trimString);
            model.addAttribute("products",productService.findAllByStockGreaterThanAndOrigin(1, origin));
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
            model.addAttribute("origins",productService.findDistinctOrigin());
            return"customer-available-origin";
        }
        String replaceString = origin.replaceAll(",", "");
        String trimString = replaceString;
        model.addAttribute("origin", trimString);
        model.addAttribute("products",productService.findAllByStockGreaterThanAndOrigin(1, origin));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        model.addAttribute("origins",productService.findDistinctOrigin());
        return"customer-available-origin";
    }

    @GetMapping("/customer/available/category/origin")
    public String getAllAvailableProductsByCategoryAndOrigin(Model model, @AuthenticationPrincipal UserDetails currentUser,
                                                             @RequestParam(value = "idCategory" , required = false) Long idCategory,
                                                  @RequestParam(value= "origin", required = false)String origin){
        if(currentUser != null){
            CustomerDto customerDto = customerService.findByUsername(currentUser.getUsername());
            model.addAttribute("customer", customerDto);
            Category category = categoryRepository.getOne(idCategory);
            String replaceString = origin.replaceAll(",", "");
            String trimString = replaceString;
            model.addAttribute("category", category);
            model.addAttribute("origin", trimString);
            model.addAttribute("products",productService.findAllByCategoryIdOriginAndStockGreaterThan(idCategory, origin, 1));
            model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
            model.addAttribute("origins",productService.findDistinctOrigin());
            return"customer-available-category-origin";
        }
        Category category = categoryRepository.getOne(idCategory);
        String replaceString = origin.replaceAll(",", "");
        String trimString = replaceString;
        model.addAttribute("category", category);
        model.addAttribute("origin", trimString);
        model.addAttribute("products",productService.findAllByCategoryIdOriginAndStockGreaterThan(idCategory, origin, 1));
        model.addAttribute("categoryList", categoryRepository.findByOrderByCategoryNameAsc());
        model.addAttribute("origins",productService.findDistinctOrigin());
        return"customer-available-category-origin";

    }
}
