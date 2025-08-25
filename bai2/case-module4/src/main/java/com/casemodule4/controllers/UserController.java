package com.casemodule4.controllers;

import com.casemodule4.dto.CreateUserDTO;
import com.casemodule4.dto.EditUserDTO;
import com.casemodule4.dto.UserDTO;
import com.casemodule4.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }
    // This controller can handle user-related requests
    // Add methods to handle user operations like listing, creating, updating, and deleting users

    @GetMapping
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        // Logic to list users
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        model.addAttribute("user", createUserDTO);
        // Logic to create a new user
        return "users/create"; // This will resolve to /WEB-INF/views/users/create.html
    }

    @GetMapping("/{id}/detail")
    public String userDetail(@PathVariable("id") String id,
                             Model model) {
        model.addAttribute("id", id);
        return "users/detail"; // This will resolve to /WEB-INF/views/users/detail.html
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        // Logic to delete a user by ID
        userService.deleteById(id);
        // For now, just redirect to the list of users
        return "redirect:/users";
    }
    //
    @PostMapping("/store")
    public String storeUser(@ModelAttribute("user") CreateUserDTO
                                    createUserDTO) throws IOException {
        // Logic to store a new user
        userService.storeUser(createUserDTO);
        return "redirect:/users";
    }
    //
    @GetMapping("/{id}/edit")
    public String showFormEdit(@PathVariable("id") int id, Model model) {
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

        // Add the EditUserDTO to the model
        model.addAttribute("user", editUserDTO);

        return "users/edit"; // This will resolve to /WEB-INF/views/users/edit.html
    }
    //
    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute("user") EditUserDTO editUserDTO) {
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/users"; // Redirect if user not found
        }
        userService.updateUser(id, editUserDTO);
        return "redirect:/users";
    }
}