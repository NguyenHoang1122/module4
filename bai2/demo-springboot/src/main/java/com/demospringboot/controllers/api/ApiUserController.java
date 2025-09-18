package com.demospringboot.controllers.api;


import com.demospringboot.dto.response.ListUserResponse;
import com.demospringboot.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiUserController {

    private UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ListUserResponse> getAll() {
        ListUserResponse data = userService.getAllUsers(0, 5);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> destroy(@PathVariable("id") String id) {
        try {
            userService.deleteById((long) Integer.parseInt(id));
            return new ResponseEntity<>("Delete user success", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Delete user error:" + e.getMessage(), HttpStatus.OK);
        }

    }

}