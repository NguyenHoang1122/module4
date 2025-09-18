package com.codegym.demo.services;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.dto.EditUserDTO;
import com.codegym.demo.dto.UserDTO;
import com.codegym.demo.dto.response.ListUserResponse;
import com.codegym.demo.dto.response.ListUserSearchResponse;
import com.codegym.demo.mappers.CreateUserMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String UPLOAD_DIR = "C:/codegym/module4/bai2/clone-spring-mvc/uploads/";
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final IRoleRepository roleRepository;
    private final FileManager fileManager;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserMapper createUserMapper;

    public UserService(IUserRepository userRepository,
                       IDepartmentRepository departmentRepository,
                       IRoleRepository roleRepository,
                       FileManager fileManager,PasswordEncoder passwordEncoder, CreateUserMapper createUserMapper) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.fileManager = fileManager;
        this.passwordEncoder = passwordEncoder;
        this.createUserMapper = createUserMapper;
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
        List<User> users = data.getContent();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getName());
            userDTO.setImageUrl(user.getImageUrl());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTO.setDepartmentName(user.getDepartment().getName());

            List<String> nameRoles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                nameRoles.add(role.getName());
            }

            userDTO.setRoleNames(nameRoles);
            userDTOs.add(userDTO);
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
    @Transactional
    public void storeUser(CreateUserDTO dto) throws IOException {
        User newUser = new User();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        newUser.setName(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(dto.getPassword()); // TODO: encode password
        newUser.setPhone(dto.getPhone());
        // Roles
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
            newUser.setRoles(roles);
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
        return userRepository.findById(id).map(this::mapToDTO).orElse(null);
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

        // Roles
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(dto.getRoleIds()));
            user.setRoles(roles);
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

        List<UserDTO> userDTOs = data.getContent().stream().map(this::mapToDTO).toList();

        ListUserResponse response = new ListUserResponse();
        response.setTotalPage(data.getTotalPages());
        response.setCurrentPage(data.getNumber());
        response.setUsers(userDTOs);

        return response;
    }

    // ✅ Search users (name/email)
    public ListUserResponse searchUsers(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());

        Page<User> data;
        if (keyword == null || keyword.isEmpty()) {
            data = userRepository.findAll(pageable);
        } else {
            data = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        }

        List<UserDTO> userDTOs = data.getContent().stream().map(this::mapToDTO).toList();

        ListUserResponse response = new ListUserResponse();
        response.setTotalPage(data.getTotalPages());
        response.setCurrentPage(data.getNumber());
        response.setUsers(userDTOs);

        return response;
    }

    // ✅ Search by name (not pageable)
    public ListUserSearchResponse searchByName(String name) {
        List<User> data = userRepository.findUserByNameContaining(name);
        List<UserDTO> userDTOList = data.stream().map(this::mapToDTO).toList();

        ListUserSearchResponse listUserSearchResponse = new ListUserSearchResponse();
        listUserSearchResponse.setUsers(userDTOList);

        return listUserSearchResponse;
    }

    // ✅ Mapping User -> UserDTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setImageUrl(user.getImageUrl());

        if (user.getDepartment() != null) {
            dto.setDepartmentId(user.getDepartment().getId());
            dto.setDepartmentName(user.getDepartment().getName());
        } else {
            dto.setDepartmentName("No Department");
        }

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            dto.setRoleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
            dto.setRoleNames(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        } else {
            dto.setRoleNames(List.of("No Role"));
        }

        return dto;
    }
}
