package com.codegym.demo.dto;

import com.codegym.demo.validations.custom.Image;
import com.codegym.demo.validations.custom.UniqueEmail;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class CreateUserDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @UniqueEmail(message = "Email address already exists")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @Image
    private MultipartFile image;

    private Long departmentId;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
