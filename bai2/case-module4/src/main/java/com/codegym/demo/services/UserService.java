package com.codegym.demo.services;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.dto.EditUserDTO;
import com.codegym.demo.dto.UserDTO;
import com.codegym.demo.dto.response.ListUserResponse;
import com.codegym.demo.models.Department;
import com.codegym.demo.models.User;
import com.codegym.demo.repositories.IDepartmentRepository;
import com.codegym.demo.repositories.IUserRepository;
import com.codegym.demo.untils.FileManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    private static final String UPLOAD_DIR = "C:/codegym/module4/bai2/case-module4/uploads";
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final FileManager fileManager;

    public UserService(IUserRepository userRepository,
                       IDepartmentRepository departmentRepository,
                       FileManager fileManager){
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.fileManager = fileManager;
    }

    public ListUserResponse getAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<User> data = userRepository.findAll(pageable);
        System.out.println("Total page: " + data.getTotalPages());
        List<User> users = data.getContent();

        // map data Entity to DTO
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId().intValue());
            userDTO.setUsername(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setImageUrl(user.getImageUrl());

            String nameDepartment = user.getDepartment() != null ? user.getDepartment().getName() : "No Department";
            userDTO.setDepartmentName(nameDepartment);

            userDTOs.add(userDTO);
        }

        ListUserResponse listUserResponse = new ListUserResponse();
        listUserResponse.setTotalPage(data.getTotalPages());
        listUserResponse.setCurrentPage(data.getNumber());
        listUserResponse.setUsers(userDTOs);

        return listUserResponse;
    }

    public void deleteById(int id){
        // Logic to delete a user by ID
        Optional<User> user = userRepository.findById((long)(id));
        if (user.isPresent()) {
            User currentUser = user.get();
            // delete image
            fileManager.deleteFile(UPLOAD_DIR + "/" + currentUser.getImageUrl());
            userRepository.delete(currentUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void storeUser(CreateUserDTO createUserDTO) {
        // Logic to store a new user
        String username = createUserDTO.getUsername();
        String password = createUserDTO.getPassword();
        String email = createUserDTO.getEmail();
        String phone = createUserDTO.getPhone();

        // Create a new User object with the provided details
        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);

        Long departmentId = createUserDTO.getDepartmentId();
        MultipartFile file = createUserDTO.getImage();

        if (!file.isEmpty()) {
            String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
            newUser.setImageUrl(fileName); // Set the image URL
        }

        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId).orElse(null);
            if (department != null) {
                newUser.setDepartment(department);
            }
        }
        userRepository.save(newUser);
    }

    public UserDTO getUserById(int id) {
        Optional<User> user = userRepository.findById((long) id);
        if (user.isPresent()) {
            User currentUser = user.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(currentUser.getId().intValue());
            userDTO.setUsername(currentUser.getName());
            userDTO.setEmail(currentUser.getEmail());
            userDTO.setPhone(currentUser.getPhone());
            userDTO.setImageUrl(currentUser.getImageUrl());
            userDTO.setDepartmentId(currentUser.getDepartment() != null ? currentUser.getDepartment().getId() : null);
            return userDTO;
        }
        return null;
    }
//
    public void updateUser(int id, EditUserDTO editUserDTO) {
        Optional<User> user = userRepository.findById((long)id);
        if (user.isPresent()) {
            // Update user details
            User currentUser = user.get();
            currentUser.setName(editUserDTO.getUsername());
            currentUser.setEmail(editUserDTO.getEmail());
            currentUser.setPhone(editUserDTO.getPhone());

            Long departmentId = editUserDTO.getDepartmentId();
            if (departmentId != null) {
                Department department = departmentRepository.findById(departmentId).orElse(null);
                if (department != null) {
                    currentUser.setDepartment(department);
                }
            }
            MultipartFile file = editUserDTO.getImage();
            if (!file.isEmpty()) {
                // 1. xoa anh cu
                fileManager.deleteFile(UPLOAD_DIR + "/" + currentUser.getImageUrl());
                String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
                currentUser.setImageUrl(fileName); // Set the image URL
            }

            userRepository.save(currentUser);
        }
    }
}
