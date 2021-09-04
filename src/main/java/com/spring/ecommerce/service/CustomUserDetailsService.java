package com.spring.ecommerce.service;

import com.spring.ecommerce.model.CustomUserDetails;
import com.spring.ecommerce.model.Role;
import com.spring.ecommerce.model.User;
import com.spring.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user));
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorityList;
    }
}
