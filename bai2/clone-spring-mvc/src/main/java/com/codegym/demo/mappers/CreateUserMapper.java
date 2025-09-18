package com.codegym.demo.mappers;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.models.Department;
import com.codegym.demo.models.Role;
import com.codegym.demo.models.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreateUserMapper {
    public CreateUserMapper(){
    }
    public User toEntity(CreateUserDTO dto, String encodedPassword,
                         Department department,
                         Set<Role> roles,
                         String imageUrl) {
        User u = new User();
        u.setName(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(encodedPassword);
        u.setPhone(dto.getPhone());
        u.setDepartment(department);
        u.setRoles(roles);
        u.setImageUrl(imageUrl);
        return u;
    }
}

