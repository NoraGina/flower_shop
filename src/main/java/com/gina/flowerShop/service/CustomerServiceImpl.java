package com.gina.flowerShop.service;

import com.gina.flowerShop.model.Customer;
import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.repository.CustomerRepository;
import com.gina.flowerShop.web.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerDto toDto(Customer customer){
        final CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFullName(customer.getFullName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        customerDto.setRoles(customer.getRoles());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhone(customer.getPhone());
        customerDto.setAddressSet(customer.getShippingAddresses());
        //customerDto.setOrderCustomerSet(customer.getOrderCustomerSet());
        return customerDto;
    }

    private Customer toEntity(CustomerDto customerDto){
        final Customer customer = new Customer();
        if(customerDto.getId() != null){
            customer.setId(customerDto.getId());
        }
        customer.setFullName(customerDto.getFullName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setRoles(customerDto.getRoles());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getEmail());
        customer.setShippingAddresses(customerDto.getAddressSet());
        //customer.setOrderCustomerSet(customerDto.getOrderCustomerSet());
        return customer;
    }
    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findAll() {

        return customerRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void save(CustomerDto customerDto) {
        final Customer customer = toEntity(customerDto);
        customerRepository.save(customer);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDto> findById(Long id) {
        return customerRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public long countByUserName(String username) {
        return customerRepository.countByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findByUsername(String username) {
        final Customer customer = customerRepository.findByUsername(username);
        final CustomerDto customerDto = toDto(customer);
        return customerDto;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findByEmailAndRoleName(String email, String name) {
        final Customer customer = customerRepository.findByEmailAndRoleName(email, name);
        final CustomerDto customerDto = toDto(customer);
        return customerDto;
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);

        if(customer == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(),mapRolesToAuthorities(customer.getRoles()));
    }

}

