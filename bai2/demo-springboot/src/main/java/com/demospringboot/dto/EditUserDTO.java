package com.demospringboot.dto;

import com.demospringboot.validations.custom.Image;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class EditUserDTO {
    private Long id;

    @NotBlank(message = "Username không được để trống")
    @Size(min = 6, max = 50, message = "Username từ 6 - 50 kí tự")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "0\\d{9}", message = "Số điện thoại phải bắt đầu bằng 0 và có 10 số")
    private String phone;

    @Image
    private MultipartFile image;

    private Long departmentId;

    // 👇 đổi từ 1 roleId thành nhiều roleIds
    @NotEmpty
    private List<Long> roleIds;

    public EditUserDTO() {
    }

    public EditUserDTO(Long id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    // getter setter ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public List<Long> getRoleIds() { return roleIds; }
    public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
}
