package com.codegym.demo.services;

import com.codegym.demo.models.User;
import com.codegym.demo.repositories.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // src/main/java/com/codegym/demo/services/CustomUserDetailsService.java
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userLogin = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                userLogin.getEmail(),
                userLogin.getPassword(),   // đảm bảo đã mã hoá bằng BCrypt
                userLogin.getRoles().stream()
                        .map(role -> {
                            String roleName = role.getName();
                            if (!roleName.startsWith("ROLE_")) {
                                roleName = "ROLE_" + roleName;
                            }
                            return new SimpleGrantedAuthority(roleName);
                        })
                        .collect(Collectors.toList())
        );
    }
}
