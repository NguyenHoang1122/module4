package com.codegym.demo.controllers;

import com.codegym.demo.dto.CreateUserDTO;
import com.codegym.demo.dto.DepartmentDTO;
import com.codegym.demo.dto.EditUserDTO;
import com.codegym.demo.dto.UserDTO;
import com.codegym.demo.dto.response.ListUserResponse;
import com.codegym.demo.models.User;
import com.codegym.demo.services.DepartmentService;
import com.codegym.demo.services.RoleService;
import com.codegym.demo.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DepartmentService departmentService;
    private final RoleService roleService;

    public UserController(UserService userService, DepartmentService departmentService, RoleService roleService) {
        this.userService = userService;
        this.departmentService = departmentService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                            @RequestParam(value = "departmentId", required = false) Long departmentId,
                            Model model) {
        if (page < 1) {
            page = 1;
        } else {
            page -= 1; // zero-based index
        }

        ListUserResponse listUserResponse;
        if (departmentId != null) {
            // Lấy danh sách user theo phòng ban
            listUserResponse = userService.getUsersByDepartment(departmentId, page, size);
        } else {
            // Lấy tất cả user
            listUserResponse = userService.getAllUsers(page, size);
        }

        model.addAttribute("users", listUserResponse.getUsers());
        model.addAttribute("totalPages", listUserResponse.getTotalPage());
        model.addAttribute("currentPage", listUserResponse.getCurrentPage());

        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("selectedDepartmentId", departmentId);

        return "users/list";
    }



    @GetMapping("/create")
    public String createUser(Model model) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        List<DepartmentDTO> departments = departmentService.getAllDepartments();

        model.addAttribute("departments", departments);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", createUserDTO);
        // Logic to create a new user
        return "users/create"; // This will resolve to /WEB-INF/views/users/create.html
    }

    @GetMapping("/{id}/detail")
    public String userDetail(@PathVariable("id") Long id, Model model) {
        UserDTO user = userService.getUserById(id); // giờ dùng Long
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/detail";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        // Logic to delete a user by ID
        userService.deleteById(id);

        return "redirect:/users";
    }
//
    @PostMapping("/store")
    public String storeUser(@Validated @ModelAttribute("user") CreateUserDTO
                                        createUserDTO, BindingResult result, Model model) throws IOException {
        if(result.hasErrors()){
            List<DepartmentDTO> departments = departmentService.getAllDepartments();

            // Add the EditUserDTO to the model

            model.addAttribute("departments", departments);
            return "users/create";
        }
        // Logic to store a new user
        userService.storeUser(createUserDTO);
        return "redirect:/users";
    }
//
@GetMapping("/{id}/edit")
public String showFormEdit(@PathVariable("id") Long id, Model model) {
    UserDTO user = userService.getUserById(id);
    if (user == null) {
        return "redirect:/users"; // Redirect if user not found
    }

    // Prepare the EditUserDTO with the user's current details
    EditUserDTO editUserDTO = new EditUserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone()
    );
    editUserDTO.setDepartmentId(user.getDepartmentId());
    editUserDTO.setRoleId(user.getRoleId());

    List<DepartmentDTO> departments = departmentService.getAllDepartments();

    // Add the EditUserDTO to the model

    model.addAttribute("departments", departments);
    model.addAttribute("roles", roleService.getAllRoles());
    model.addAttribute("user", editUserDTO);

    return "users/edit"; // This will resolve to /WEB-INF/views/users/edit.html
}
//
    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") EditUserDTO editUserDTO) throws IOException {
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users"; // Redirect if user not found
        }
        userService.updateUser(id, editUserDTO);
        return "redirect:/users";
    }
}
