package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.Customer;
import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.model.ShippingAddress;
import com.gina.flowerShop.repository.CategoryRepository;
import com.gina.flowerShop.repository.CustomerRepository;
import com.gina.flowerShop.repository.RoleRepository;
import com.gina.flowerShop.repository.ShippingAddressRepository;
import com.gina.flowerShop.service.CustomerService;
import com.gina.flowerShop.service.ProductService;
import com.gina.flowerShop.web.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CustomerController {
    @Autowired private CustomerService customerService;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ShippingAddressRepository shippingAddressRepository;
    @Autowired private CustomerRepository customerRepository;

    @GetMapping("/customer/registration")
    public String customerAddForm(Model model){

         CustomerDto customerDto = new CustomerDto();

        model.addAttribute("customer",customerDto);
        return "customer-registration";
    }

    @PostMapping("/customer/registration")
    public String addCustomer(@Valid @ModelAttribute("customer")CustomerDto customerDto, BindingResult result,
                              RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            return "customer-registration";
        }

        if(customerService.countByUserName(customerDto.getUsername())==1L){
            redirectAttributes.addFlashAttribute("message", "Exista deja un cont la acest utilizator "+customerDto.getUsername());
            return "redirect:/customer/login";
        }
        Set<Role> roleSet= new HashSet<>();
        //roleSet.add(new Role("ROLE_CLIENT"));
        roleSet.add(roleRepository.findByName("ROLE_CLIENT"));
        customerDto.setRoles(roleSet);
        customerService.save(customerDto);

        return "redirect:/customer/registration?success";
    }

    @GetMapping("/customer/update/form")
    public String displayCustomerUpdate(@RequestParam("email") String email,
                                        RedirectAttributes redirectAttributes,Model model){

       CustomerDto customer = customerService.findByEmailAndRoleName(email,"ROLE_CLIENT" );
        if(customer != null){
            model.addAttribute("customer", customer);

            return "customer-update";

        }
        redirectAttributes.addFlashAttribute("message", "Nu exista cont pe acest "+email+", inregistrati-va! ");
        return "redirect:/";
    }

    @PostMapping("customer/update/{id}")
    public String updateCustomer(@PathVariable("id")Long id, Model model,
                                 @Valid @ModelAttribute("customer") CustomerDto customer, BindingResult result,
                                 RedirectAttributes attributes){
        if(result.hasErrors()){
            return "customer-update";
        }
        Set<Role> roleSet= new HashSet<>();
        roleSet.add(roleRepository.findByName("ROLE_CLIENT"));
        customer.setRoles(roleSet);
        customerService.save(customer);
        model.addAttribute("customer", customer);

        attributes.addFlashAttribute("message", customer.getFullName().toUpperCase()+" "+"editare cu succes!");

        return "redirect:/";
    }


    @GetMapping("/customer/add/address/form")
    public String customerDisplayAddAddressForm(Model model, @AuthenticationPrincipal UserDetails currentUser){
        Customer customer =customerRepository.findByUsername(currentUser.getUsername());
        model.addAttribute("customer", customer);

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCustomer(customer);
        model.addAttribute("shippingAddress", shippingAddress);
        return "customer-add-address";
    }

    @PostMapping("/customer/add/address")
    public String customerAddAddress(@Valid @ModelAttribute("shippingAddress")ShippingAddress shippingAddress,
                                     BindingResult result, RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal UserDetails currentUser, Model model){
        if(result.hasErrors()){
            return "customer-add-address";
        }
        try{
            Customer customer = customerRepository.findByUsername(currentUser.getUsername());
            if(shippingAddressRepository.countByNameAndCustomerId(shippingAddress.getName(), customer.getId())==1L){
                redirectAttributes.addFlashAttribute("message", shippingAddress.getName()+" are adresa poate doriti sa o editati?!");
                return "redirect:/customer/shippingAddresses";
            }

            shippingAddress.setCustomer(customer);
            shippingAddressRepository.save(shippingAddress);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ShippingAddress newShippingAddress = new ShippingAddress();
            final Optional<Customer> customerOptional = customerRepository.findById(shippingAddress.getCustomer().getId());
            if(customerOptional.isPresent()){
                final Customer customer = customerOptional.get();
                newShippingAddress.setCustomer(customer);
                model.addAttribute("shippingAddress", newShippingAddress);
            }
            model.addAttribute("customer", shippingAddress.getCustomer());

        }

        return "customer-add-address";
    }

    @GetMapping("/customer/shippingAddresses")
    public String displayCustomerShippingAddresses(Model model, @AuthenticationPrincipal UserDetails currentUser){
        Customer customer = customerRepository.findByUsername(currentUser.getUsername());

        Set<ShippingAddress> shippingAddressList = new HashSet<>();
        List<ShippingAddress> findShippingAddressList = shippingAddressRepository.findAllByCustomerId(customer.getId());
        for(ShippingAddress shippingAddress:findShippingAddressList){

            shippingAddressList.add(shippingAddress);
        }

        model.addAttribute("shippingAddresses", shippingAddressList);
        return "customer-addresses";
    }



    @GetMapping("/customer/shippingAddress/update/form/{idShippingAddress}")
    public  String displayUpdateFormAddress(@PathVariable("idShippingAddress")Long idShippingAddress, Model model,
                                            @AuthenticationPrincipal UserDetails currentUser){
        Optional<ShippingAddress>optionalShippingAddress = shippingAddressRepository.findById(idShippingAddress);
        if(optionalShippingAddress.isPresent()){
            final ShippingAddress shippingAddress = optionalShippingAddress.get();

            Customer customer = customerRepository.findByUsername(currentUser.getUsername());
            shippingAddress.setCustomer(customer);
            model.addAttribute("customer", customer);
            model.addAttribute("shippingAddress", shippingAddress);
        }else{
            new IllegalArgumentException("Invalid id shipping address"+idShippingAddress);
        }

        return "customer-update-address";
    }

    @PostMapping("/customer/update/address/{idShippingAddress}")
    public String customerUpdateAddress(@PathVariable("idShippingAddress") Long idShippingAddress,
                                        @Valid @ModelAttribute("shippingAddress")ShippingAddress shippingAddress,
                                        RedirectAttributes redirectAttributes, Model model, BindingResult result){
        if(result.hasErrors()){
            return "customer-update-address";
        }

        redirectAttributes.addFlashAttribute("message", "Adresa de livrare "+shippingAddress.getName()+"  editata cu succes");
        shippingAddress.setCustomer(shippingAddress.getCustomer());

        shippingAddressRepository.save(shippingAddress);

        return"redirect:/customer/shippingAddresses";
    }

    @GetMapping("/customer/delete/shippingAddress/{idShippingAddress}")
    public String customerDeleteShippingAddress(@PathVariable("idShippingAddress")Long idShippingAddress,
                                                RedirectAttributes redirectAttributes){
        ShippingAddress shippingAddress = shippingAddressRepository.findById(idShippingAddress).get();
        redirectAttributes.addFlashAttribute("message", shippingAddress.getName()+" a fost stearsa");
        shippingAddressRepository.deleteById(idShippingAddress);

        return "redirect:/customer/shippingAddresses";
    }
}
