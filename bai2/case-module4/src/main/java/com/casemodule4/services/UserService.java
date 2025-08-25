package com.casemodule4.services;

import com.casemodule4.dto.CreateUserDTO;
import com.casemodule4.dto.EditUserDTO;
import com.casemodule4.dto.UserDTO;
import com.casemodule4.models.Department;
import com.casemodule4.models.User;
import com.casemodule4.repositories.IDepartmentRepository;
import com.casemodule4.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String UPLOAD_DIR = "C:/codegym/module4/bai2/case-module4/src/main/webapp/WEB-INF/resources/uploads";
    private IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;

    public UserService(IUserRepository userRepository, IDepartmentRepository departmentRepository){
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
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
        return userDTOs;
    }
    //
    public void deleteById(int id){
        // Logic to delete a user by ID
        Optional<User> user = userRepository.findById((long)(id));
        if (user.isPresent()) {
            User currentUser = user.get();
            // delete image
            File imageFile = new File(UPLOAD_DIR + "/" + currentUser.getImageUrl());
            if (imageFile.exists()) {
                imageFile.delete(); // Delete the image file
            } else {
                System.out.println("Image file not found: " + imageFile.getAbsolutePath());
            }
            userRepository.delete(currentUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    //
    public void storeUser(CreateUserDTO createUserDTO) throws IOException {
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
        System.out.println("File: " + file);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File dest = new File(UPLOAD_DIR + "/" + fileName);
            file.transferTo(dest);
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
            userRepository.save(currentUser);
        }
    }
}