package com.gina.flowerShop.service;

import com.gina.flowerShop.model.Role;
import com.gina.flowerShop.model.User;
import com.gina.flowerShop.repository.UserRepository;
import com.gina.flowerShop.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;

    private User toEntity(UserDto userDto){
        final User user = new User();
        if(userDto.getId()!=null){
            user.setId(userDto.getId());
        }
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setPhone(userDto.getPhone());
        user.setRoles(userDto.getRoles());
        return user;
    }

    private UserDto toDto(User user){
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        final UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
