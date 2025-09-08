package com.codegym.demo.services;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.dto.EditUserDTO;
import com.codegym.demo.dto.UserDTO;
import com.codegym.demo.dto.response.ListUserResponse;
import com.codegym.demo.dto.response.ListUserSearchResponse;
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
    private static final String UPLOAD_DIR = "C:/codegym/module4/bai2/clone-spring-mvc/uploads/";
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final IRoleRepository roleRepository;
    private final FileManager fileManager;

    public UserService(IUserRepository userRepository,
                       IDepartmentRepository departmentRepository,
                       IRoleRepository roleRepository,
                       FileManager fileManager) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.fileManager = fileManager;
    }

    // ✅ Validation ảnh
    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        long maxSize = 5 * 1024 * 1024; // 5MB
        return contentType != null &&
                (contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/gif")) &&
                file.getSize() <= maxSize;
    }

    // ✅ Lấy danh sách user có phân trang
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

    // ✅ Xóa user
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (user.getImageUrl() != null) {
            fileManager.deleteFile(UPLOAD_DIR + "/" + user.getImageUrl());
        }
        userRepository.delete(user);
    }

    // ✅ Thêm mới user
    public void storeUser(CreateUserDTO dto) throws IOException {
        User newUser = new User();
        newUser.setName(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(dto.getPassword());
        newUser.setPhone(dto.getPhone());

        // Role
        if (dto.getRoleId() != null) {
            roleRepository.findById(dto.getRoleId()).ifPresent(newUser::setRole);
        }

        // Department
        if (dto.getDepartmentId() != null) {
            departmentRepository.findById(dto.getDepartmentId()).ifPresent(newUser::setDepartment);
        }

        // Image
        MultipartFile file = dto.getImage();
        if (file != null && !file.isEmpty()) {
            if (!isValidImage(file)) {
                throw new IllegalArgumentException("Invalid file format or file too large (max 5MB, jpg/png/gif only).");
            }
            String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
            newUser.setImageUrl(fileName);
        }

        userRepository.save(newUser);
    }

    // ✅ Lấy user theo ID
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

    // ✅ Cập nhật user
    public void updateUser(Long id, EditUserDTO dto) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // Department
        if (dto.getDepartmentId() != null) {
            departmentRepository.findById(dto.getDepartmentId()).ifPresent(user::setDepartment);
        }

        // Role
        if (dto.getRoleId() != null) {
            roleRepository.findById(dto.getRoleId()).ifPresent(user::setRole);
        }

        // Image
        MultipartFile file = dto.getImage();
        if (file != null && !file.isEmpty()) {
            if (!isValidImage(file)) {
                throw new IllegalArgumentException("Invalid file format or file too large (max 5MB, jpg/png/gif only).");
            }
            // Xóa file cũ chỉ khi upload file mới
            if (user.getImageUrl() != null) {
                fileManager.deleteFile(UPLOAD_DIR + "/" + user.getImageUrl());
            }
            String fileName = fileManager.uploadFile(UPLOAD_DIR, file);
            user.setImageUrl(fileName);
        }
        // Nếu file rỗng, giữ nguyên ảnh cũ

        userRepository.save(user);
    }

    // ✅ Lấy danh sách user theo departmentId (phân trang)
    public ListUserResponse getUsersByDepartment(Long departmentId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<User> data;

        if (departmentId == null || departmentId == 0) {
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
    public ListUserResponse searchUsers(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());

        Page<User> data;
        if (keyword == null || keyword.isEmpty()) {
            data = userRepository.findAll(pageable);
        } else {
            data = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
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
    public ListUserSearchResponse searchByName(String name) {
        List<User> data = userRepository.findUserByNameContaining(name);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : data) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setImageUrl(user.getImageUrl());

            String nameDepartment = user.getDepartment() != null ? user.getDepartment().getName() : "No Department";
            userDTO.setDepartmentName(nameDepartment);

            userDTOList.add(userDTO);
        }

        ListUserSearchResponse listUserSearchResponse = new ListUserSearchResponse();
        listUserSearchResponse.setUsers(userDTOList);

        return listUserSearchResponse;
    }
}
