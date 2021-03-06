package com.gina.flowerShop.service;

import com.gina.flowerShop.model.Provider;
import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.repository.ProviderRepository;
import com.gina.flowerShop.web.dto.ProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProviderServiceImpl implements ProviderService{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired private ProviderRepository providerRepository;

    private ProviderDto toDto(Provider provider){
        final ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setFullName(provider.getFullName());
        providerDto.setUsername(provider.getUsername());
        providerDto.setPassword(provider.getPassword());
        providerDto.setEmail(provider.getEmail());
        providerDto.setPhone(provider.getPhone());
        providerDto.setRoles(provider.getRoles());

        return providerDto;
    }

    private Provider toEntity(ProviderDto providerDto){
        final Provider provider = new Provider();
        if(providerDto.getId() != null){
            provider.setId(providerDto.getId());
        }
        provider.setFullName(providerDto.getFullName());
        provider.setUsername(providerDto.getUsername());
        provider.setPassword(passwordEncoder.encode(providerDto.getPassword()));
        provider.setEmail(providerDto.getEmail());
        provider.setPhone(providerDto.getPhone());
        provider.setRoles(providerDto.getRoles());
        return provider;
    }


    @Override
    @Transactional(readOnly = true)
    public ProviderDto findByUsername(String username) {
        Provider provider = providerRepository.findByUsername(username);
        return toDto(provider);
    }

    @Override
    public List<ProviderDto> findAll() {
        List<Provider>providerList = providerRepository.findAll();
        List<ProviderDto>providerDtoList = new ArrayList<>();
        for(Provider provider:providerList){
            final ProviderDto providerDto= toDto(provider);
            providerDtoList.add(providerDto);
        }
        return providerDtoList;
    }

    @Override
    public void save(ProviderDto providerDto) {
        final Provider provider = toEntity(providerDto);
        providerRepository.save(provider);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProviderDto> findById(Long idUser) {
        return providerRepository.findById(idUser).map(this::toDto);
    }

    @Override
    public void delete(Long idUser) {
        providerRepository.deleteById(idUser);
    }

    @Override
    public long countByUserName(String username) {
        return providerRepository.countByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Provider provider = providerRepository.findByUsername(username);

        if(provider == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(provider.getUsername(), provider.getPassword(),mapRolesToAuthorities(provider.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
