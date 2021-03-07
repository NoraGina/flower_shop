package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.service.ProductService;
import com.gina.flowerShop.service.UserService;
import com.gina.flowerShop.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("products", productService.findAll());

        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
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

}
