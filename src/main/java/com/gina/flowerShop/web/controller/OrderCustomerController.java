package com.gina.flowerShop.web.controller;

import com.gina.flowerShop.model.*;
import com.gina.flowerShop.repository.*;
import com.gina.flowerShop.service.OrderCustomerService;
import com.gina.flowerShop.service.ProductService;
import com.gina.flowerShop.web.dto.CustomerDto;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
public class OrderCustomerController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired private ProductService productService;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private HttpSession httpSession;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private ShippingAddressRepository shippingAddressRepository;
    @Autowired private OrderCustomerService orderCustomerService;
    @Autowired private OrderCustomerRepository orderCustomerRepository;

    @GetMapping("/by/product/{idProduct}")
    public String byProduct(@PathVariable(value = "idProduct", required = false)Long idProduct, @AuthenticationPrincipal UserDetails currentUser,
                            Model model){
        try{
            Customer customer =customerRepository.findByUsername(currentUser.getUsername());
            if(customer !=null){
                if(httpSession.getAttribute("order")==null){
                    List<OrderItem> orderItemList = new ArrayList<>();
                    OrderCustomer orderCustomer = new OrderCustomer();

                    OrderItem orderItem = new OrderItem(productRepository.findProductByIdProduct(idProduct), 1, orderCustomer);

                    orderItemList.add(orderItem);
                    orderCustomer.setStatus(Status.AFFECTED);
                    orderCustomer.setDate(LocalDate.now());
                    orderCustomer.setTime(LocalTime.now());
                    orderCustomer.setOrderItemList(orderItemList);

                    orderCustomer.setCustomer(customer);
                    httpSession.setAttribute("order", orderCustomer);
                    model.addAttribute("orderCustomer", orderCustomer);


                }else{
                    OrderCustomer orderCustomer = (OrderCustomer) httpSession.getAttribute("order");
                    List<OrderItem>orderItems = orderCustomer.getOrderItemList();
                    int index = this.exists(idProduct,  orderItems);
                    if(index == -1){
                        OrderItem newOrderItem = new OrderItem(productRepository.findById(idProduct).get(), 1);
                        orderItems.add(newOrderItem);

                        newOrderItem.setOrderCustomer(orderCustomer);
                    }else{
                        int quantity = ( orderItems).get(index).getQuantity()+1;
                        orderItems.get(index).setQuantity(quantity);

                    }
                    orderCustomer.setOrderItemList(orderItems);
                    httpSession.setAttribute("order", orderCustomer);
                    model.addAttribute("orderCustomer", orderCustomer);

                }

                return "redirect:/customer/byPage";
            }else{
                model.addAttribute("customer", new CustomerDto());
                return "customer-registration";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "index";


    }

    private int exists(Long idProduct, List<OrderItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getIdProduct().equals(idProduct)) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping("/remove/{idProduct}")
    public String removeItem(@PathVariable("idProduct") Long idProduct, @AuthenticationPrincipal UserDetails currentUser,
                             Model model) {
        OrderCustomer orderCustomer = (OrderCustomer) httpSession.getAttribute("order");
        List<OrderItem> orderItemList =  orderCustomer.getOrderItemList();
        int index = this.exists(idProduct, orderItemList);
        orderItemList.remove(index);
        double total=0;

        for(OrderItem orderItem:orderItemList){
            total+=orderItem.getProduct().getPrice()*orderItem.getQuantity();

        }
        orderCustomer.setOrderItemList(orderItemList);
        model.addAttribute("total", total);

        return "redirect:/customer/view/cart/form";
    }

    @GetMapping("/customer/view/cart/form")
    public String customerViewCart(Model model, @AuthenticationPrincipal UserDetails currentUser,
                                   RedirectAttributes redirectAttributes){
        if(httpSession.getAttribute("order") !=null){
            OrderCustomer orderCustomer = (OrderCustomer) httpSession.getAttribute("order");
            orderCustomer.setStatus(Status.AFFECTED);
            orderCustomer.setDate(LocalDate.now());
            orderCustomer.setTime(LocalTime.now());
            List<OrderItem>orderItemList = orderCustomer.getOrderItemList();
            double total=0;

            for(OrderItem orderItem:orderItemList){
                total+=orderItem.getProduct().getPrice()*orderItem.getQuantity();

            }

            //orderCustomer.setOrderItemList(orderItemList);
            Customer customer = customerRepository.findByUsername(currentUser.getUsername());
            orderCustomer.setCustomer(customer);
            model.addAttribute("customer", customer);
            model.addAttribute("total", total);
            model.addAttribute("shippingAddresses", shippingAddressRepository.findAllByCustomerId(customer.getId()));
            model.addAttribute("items", orderItemList);
            return "customer-view-cart";
        }
        Customer customer = customerRepository.findByUsername(currentUser.getUsername());
        redirectAttributes.addFlashAttribute("message", customer.getFullName()+", nu aveti produse in cos");
        return "redirect:/customer/byPage";
    }



    @GetMapping("/customer/cart/form")
    public String displayCartForm(@AuthenticationPrincipal UserDetails currentUser, Model model){
        OrderCustomer orderCustomer = (OrderCustomer) httpSession.getAttribute("order");

        //orderCustomer.setStatus(Status.AFFECTED);
        //orderCustomer.setDate(LocalDate.now());
        //orderCustomer.setTime(LocalTime.now());
        List<OrderItem>orderItemList = orderCustomer.getOrderItemList();
        double total=0;
        double value = 0;
        for(OrderItem orderItem:orderItemList){
            total+=orderItem.getProduct().getPrice()*orderItem.getQuantity();
            value = orderItem.getProduct().getPrice()*orderItem.getQuantity();
        }
        orderCustomer.setOrderItemList(orderItemList);
        Customer customer = customerRepository.findByUsername(currentUser.getUsername());
        orderCustomer.setCustomer(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("total", total);
        model.addAttribute("value", value);
        Set<ShippingAddress> shippingAddresses = new HashSet<>();
        List<ShippingAddress>findShippingAddressList = shippingAddressRepository.findAllByCustomerId(customer.getId());
        for(ShippingAddress shippingAddress:findShippingAddressList){

            shippingAddresses.add(shippingAddress);
        }
        model.addAttribute("shippingAddresses", shippingAddresses);
        model.addAttribute("orderCustomer", orderCustomer);

        return "customer-save-cart";
    }

    @PostMapping("/customer/save/order")
    public String saveOrder(@Valid @ModelAttribute("orderCustomer") OrderCustomer orderCustomer,
                            BindingResult result, @AuthenticationPrincipal UserDetails currentUser,
                            Model model){
        if(result.hasErrors()){
            return "customer-save-cart";
        }
        Customer customer = customerRepository.findByUsername(currentUser.getUsername());
        double total = 0;
        for(OrderItem orderItem:orderCustomer.getOrderItemList()){
            total+=orderItem.getProduct().getPrice()*orderItem.getQuantity();
        }
        orderCustomer.getOrderItemList().forEach(item -> item.setOrderCustomer(orderCustomer));
        orderCustomer.setCustomer(customer);
        orderCustomer.setStatus(Status.AFFECTED);
        orderCustomer.setDate(LocalDate.now());
        orderCustomer.setTime(LocalTime.now());
        orderCustomerRepository.save(orderCustomer);
        model.addAttribute("total", total);
        model.addAttribute("orderCustomer", orderCustomer);
        model.addAttribute("customer", customer);
        httpSession.removeAttribute("order");

        return "customer-order";
    }

    @GetMapping("/customer/order")
    private String displayCustomerOrder(Model model){

        return "customer-order";
    }

    @GetMapping("/customer/update/order/form/{idOrderCustomer}")
    public String displayUpdateOrderForm(@PathVariable("idOrderCustomer") Long idOrderCustomer,
                                         Model model){
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

        }else {
            new IllegalArgumentException("Invalid order Id:" + idOrderCustomer);
        }

        return "customer-update-order";
    }



    @PostMapping("/customer/update/order/{idOrderCustomer}")
    public String customerUpdateOrder(@PathVariable(value="idOrderCustomer", required = false) Long idOrderCustomer,
                                      @Valid @ModelAttribute("orderCustomer") OrderCustomer orderCustomer,
                                      BindingResult result,@AuthenticationPrincipal UserDetails currentUser,
                                      RedirectAttributes attributes, Model model){
        if(result.hasErrors()){
            return "customer-update-order";
        }
        Customer customer = customerRepository.findByUsername(currentUser.getUsername());
        orderCustomer.setCustomer(customer);
        ShippingAddress shippingAddress = orderCustomer.getShippingAddress();
        shippingAddress.setCustomer(customer);

        double total = 0;
        double value =0;

        for(OrderItem orderItem:orderCustomer.getOrderItemList()){
            value = orderItem.getProduct().getPrice()*orderItem.getQuantity();
            total += orderItem.getProduct().getPrice()*orderItem.getQuantity();
        }

        orderCustomer.setOrderItemList(orderCustomer.getOrderItemList());
        orderCustomer.getOrderItemList().forEach(item -> item.setOrderCustomer(orderCustomer));
        orderCustomerRepository.save(orderCustomer);

        model.addAttribute("orderCustomer", orderCustomer);
        model.addAttribute("customer", orderCustomer.getCustomer());
        model.addAttribute("total", total);
        model.addAttribute("value", value);
        attributes.addFlashAttribute("message", "Comanda a fost editata cu succes!");

        return "customer-order";
    }

    @Transactional
    @GetMapping("/customer/delete/order/{idOrderCustomer}")
    public String deleteOrder(@PathVariable("idOrderCustomer") Long idOrderCustomer, Model model,
                              RedirectAttributes attributes) {

        Customer customer = orderCustomerRepository.findById(idOrderCustomer).get().getCustomer();
        OrderCustomer orderCustomer = orderCustomerRepository.findById(idOrderCustomer).get();
        List<ShippingAddress>shippingAddressList = shippingAddressRepository.findAllByCustomerId(customer.getId());
        ShippingAddress shippingAddress = orderCustomer.getShippingAddress();

        attributes.addFlashAttribute("message", customer.getFullName()+ ", Doriti sa faceti alta comanda? Sau sa iesiti din aplicatie!");
        orderCustomerRepository.deleteById(idOrderCustomer);
        shippingAddressList.add(shippingAddress);
        Set<ShippingAddress>shippingAddressSet = new HashSet<>(shippingAddressList);
        customer.setShippingAddresses(shippingAddressSet);
        return "redirect:/customer/byPage";

    }

}
