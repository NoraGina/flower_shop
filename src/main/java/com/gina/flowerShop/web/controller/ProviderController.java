package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.*;
import com.gina.flowerShop.repository.*;
import com.gina.flowerShop.service.ProviderService;
import com.gina.flowerShop.web.dto.ProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
public class ProviderController {
    @Autowired private ProviderService providerService;

    @Autowired private RoleRepository roleRepository;

    @Autowired private OrderCustomerRepository orderCustomerRepository;

    @Autowired private ShippingAddressRepository shippingAddressRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private CustomerRepository customerRepository;

    @GetMapping("/admin/registration")
    public String showRegistrationFormProvider(Model model) {
        ProviderDto provider = new ProviderDto();
        model.addAttribute("roleList", roleRepository.findFirst2ByOrderByIdRole());
        model.addAttribute("provider", provider);
        return "admin-registration";
    }

    @PostMapping("/admin/registration")
    public String registerProviderAccount(@Valid @ModelAttribute("provider") ProviderDto providerDto, BindingResult result,
                                          RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()){

            return "admin-registration";

        }
        if(providerService.countByUserName(providerDto.getUsername()) == 1l){
            redirectAttributes.addFlashAttribute("message", providerDto.getUsername()+" already have account");

            return "redirect:/admin/providers/list";
        }

       /* Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role("ROLE_ADMIN"));
        roleSet.add(new Role("ROLE_STAFF"));

        providerDto.setRoles(roleSet);
        Set<ProviderDto> userSet = new HashSet<>();
        userSet.add(providerDto);*/

        providerService.save(providerDto);
        model.addAttribute("provider", providerDto);
        return "redirect:/admin/registration?success";
    }

    @GetMapping("/admin/providers/list")
    public String displayProvidersList(Model model) {
        model.addAttribute("providers", providerService.findAll());

        return "admin-providers-list";
    }

    @GetMapping("/admin/delete/provider/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        ProviderDto providerDto = providerService.findById(id).get();
        redirectAttributes.addFlashAttribute("message","Utilizatorul "+providerDto.getFullName()+ " a fost sters!");
        providerService.delete(id);

        return "redirect:/admin/providers/list";
    }

    @GetMapping("/admin/update/form/{id}")
    public String displayUpdateFormProvider(@PathVariable("id") Long id, Model model){
        final Optional<ProviderDto> optionalProviderDto = providerService.findById(id);
        if (optionalProviderDto.isPresent()) {
            final ProviderDto providerDto = optionalProviderDto.get();
            List<Role> roleList = roleRepository.findFirst2ByOrderByIdRole();
            model.addAttribute("roleList", roleList);
            model.addAttribute("providers", providerService.findAll());
            model.addAttribute("provider", providerDto);
        } else {
            new IllegalArgumentException("Invalid user Id:" + id);
        }

        return "admin-update-provider";
    }

    @PostMapping("/admin/update/provider/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("provider") ProviderDto providerDto,
                             BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-update-provider";
        }

        providerService.save(providerDto);
        redirectAttributes.addFlashAttribute("message", "Utilizatorul "+providerDto.getFullName()+" a fost editat cu succes");

        return "redirect:/admin/providers/list";
    }

    @GetMapping("/provider/orders")
    public String getAllOrders( Model model){
        List<OrderCustomer>orderCustomers = orderCustomerRepository.findAll();
        double total = 0;
        for(OrderCustomer orderCustomer: orderCustomers){
            total += orderCustomer.getTotal();
            model.addAttribute("orderCustomer", orderCustomer);
            model.addAttribute("customer", orderCustomer.getCustomer());
        }

        model.addAttribute("orders", orderCustomers);
        model.addAttribute("total", total);
        return "provider-orders";
    }
    @Transactional
    @GetMapping("/provider/delete/order/{idOrderCustomer}")
    public String providerDeleteOrder(@PathVariable("idOrderCustomer")Long idOrderCustomer, Model model,
                                      RedirectAttributes redirectAttributes){
        Customer customer = orderCustomerRepository.findById(idOrderCustomer).get().getCustomer();
        OrderCustomer orderCustomer = orderCustomerRepository.getOne(idOrderCustomer);
        List<ShippingAddress>shippingAddressList = shippingAddressRepository.findAllByCustomerId(customer.getId());
        ShippingAddress shippingAddress = orderCustomer.getShippingAddress();
        redirectAttributes.addFlashAttribute("message", "Comanda "+idOrderCustomer+ " a fost stearsa cu succes");
        orderCustomerRepository.deleteById(idOrderCustomer);
        shippingAddressList.add(shippingAddress);
        Set<ShippingAddress>shippingAddressSet = new HashSet<>(shippingAddressList);
        customer.setShippingAddresses(shippingAddressSet);

        return "redirect:/provider/orders";
    }

    @GetMapping("/provider/update/order/form/{idOrderCustomer}")
    public String displayProviderUpdateOrderForm(@PathVariable("idOrderCustomer")Long idOrderCustomer, Model model){
        Optional<OrderCustomer> optionalOrderCustomer = orderCustomerRepository.findById(idOrderCustomer);
        if(optionalOrderCustomer.isPresent()){
            final OrderCustomer orderCustomer = optionalOrderCustomer.get();
            Customer customer = orderCustomer.getCustomer();
            ShippingAddress shippingAddress = orderCustomer.getShippingAddress();
            orderCustomer.setOrderItemList(orderCustomer.getOrderItemList());
            orderCustomer.getOrderItemList().forEach(item -> item.setOrderCustomer(orderCustomer));

            model.addAttribute("orderCustomer", orderCustomer);
            model.addAttribute("customer", customer);
            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("orderItems", orderCustomer.getOrderItemList());
        }else {
            new IllegalArgumentException("Invalid order Id:" + idOrderCustomer);
        }

        return "provider-update-order";
    }

    @PostMapping("/provider/update/order/{idOrderCustomer}")
    public String providerUpdateOrder(@PathVariable("idOrderCustomer")Long idOrderCustomer,
                                      @Valid @ModelAttribute("orderCustomer")OrderCustomer orderCustomer,
                                      RedirectAttributes redirectAttributes, BindingResult result){

            if(result.hasErrors()){
                return "provider-update-order";
            }
            Customer customer = orderCustomer.getCustomer();
            orderCustomer.setCustomer(customer);
            ShippingAddress shippingAddress = orderCustomer.getShippingAddress();
            shippingAddress.setCustomer(customer);
            orderCustomer.setOrderItemList(orderItemRepository.findAllByIdOrderCustomer(orderCustomer.getIdOrderCustomer()));

            orderCustomer.getOrderItemList().forEach(item -> item.setOrderCustomer(orderCustomer));
            redirectAttributes.addFlashAttribute("message","Comanda "+idOrderCustomer+ " a fost editata cu succes" );
            orderCustomerRepository.save(orderCustomer);

        return "redirect:/provider/orders";
    }
}
