package com.codegym.demo.services;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.dto.EditUserDTO;
import com.codegym.demo.dto.UserDTO;
import com.codegym.demo.dto.response.ListUserResponse;
import com.codegym.demo.models.Department;
import com.codegym.demo.models.Role;
import com.codegym.demo.models.User;
import com.codegym.demo.repositories.IDepartmentRepository;
import com.codegym.demo.repositories.IRoleRepository;
import com.codegym.demo.repositories.IUserRepository;
import com.codegym.demo.untils.FileManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    private static final String UPLOAD_DIR = "C:/codegym/module4/bai2/clone-spring-mvc/uploads";
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final FileManager fileManager;
    private final IRoleRepository roleRepository;

    public UserService(IUserRepository userRepository,
                       IDepartmentRepository departmentRepository,
                       FileManager fileManager,
                       IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.fileManager = fileManager;
        this.roleRepository = roleRepository;
    }

    // Lấy danh sách user có phân trang
    public ListUserResponse getAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<User> data = userRepository.findAll(pageable);

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : data.getContent()) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setImageUrl(user.getImageUrl());
            dto.setDepartmentName(user.getDepartment() != null ? user.getDepartment().getName() : "No Department");
            dto.setRoleName(user.getRole() != null ? user.getRole().getName() : "No Role");
            userDTOs.add(dto);
        }

        ListUserResponse response = new ListUserResponse();
        response.setTotalPage(data.getTotalPages());
        response.setCurrentPage(data.getNumber());
        response.setUsers(userDTOs);

        return response;
    }

    // Xóa user theo ID
    public void deleteById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getImageUrl() != null) {
                fileManager.deleteFile(UPLOAD_DIR + "/" + user.getImageUrl());
            }
            userRepository.delete(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Thêm mới user
    public void storeUser(CreateUserDTO dto) throws IOException {
        User newUser = new User();
        newUser.setName(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(dto.getPassword());
        newUser.setPhone(dto.getPhone());

        // Role
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId()).orElse(null);
            newUser.setRole(role);
        }

        // Department
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
            newUser.setDepartment(department);
        }

        // Image
        MultipartFile file = dto.getImage();
        if (file != null && !file.isEmpty()) {
            String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
            newUser.setImageUrl(fileName);
        }

        userRepository.save(newUser);
    }

    // Lấy user theo ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(u -> {
            UserDTO dto = new UserDTO();
            dto.setId(u.getId());
            dto.setUsername(u.getName());
            dto.setEmail(u.getEmail());
            dto.setPhone(u.getPhone());
            dto.setImageUrl(u.getImageUrl());
            dto.setDepartmentId(u.getDepartment() != null ? u.getDepartment().getId() : null);
            dto.setRoleId(u.getRole() != null ? u.getRole().getId() : null);
            return dto;
        }).orElse(null);
    }

    // Cập nhật user
    public void updateUser(Long id, EditUserDTO dto) throws IOException {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());

            // Cập nhật department
            if (dto.getDepartmentId() != null) {
                Department department = departmentRepository.findById(dto.getDepartmentId()).orElse(null);
                user.setDepartment(department);
            }

            // Cập nhật role
            if (dto.getRoleId() != null) {
                Role role = roleRepository.findById(dto.getRoleId()).orElse(null);
                user.setRole(role);
            }

            // Cập nhật ảnh
            MultipartFile file = dto.getImage();
            if (file != null && !file.isEmpty()) {
                if (user.getImageUrl() != null) {
                    fileManager.deleteFile(UPLOAD_DIR + "/" + user.getImageUrl());
                }
                String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
                user.setImageUrl(fileName);
            }

            userRepository.save(user);
        }
    }
    // Lấy danh sách user theo departmentId có phân trang
    public ListUserResponse getUsersByDepartment(Long departmentId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<User> data;

        if (departmentId == null || departmentId == 0) {
            // Nếu không chọn phòng ban thì lấy tất cả user
            data = userRepository.findAll(pageable);
        } else {
            data = userRepository.findByDepartmentId(departmentId, pageable);
        }

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : data.getContent()) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setImageUrl(user.getImageUrl());
            dto.setDepartmentName(user.getDepartment() != null ? user.getDepartment().getName() : "No Department");
            dto.setRoleName(user.getRole() != null ? user.getRole().getName() : "No Role");
            userDTOs.add(dto);
        }

        ListUserResponse response = new ListUserResponse();
        response.setTotalPage(data.getTotalPages());
        response.setCurrentPage(data.getNumber());
        response.setUsers(userDTOs);

        return response;
    }
}