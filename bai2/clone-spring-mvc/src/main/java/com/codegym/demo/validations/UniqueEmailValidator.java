package com.codegym.demo.validations;

import com.codegym.demo.repositories.IUserRepository;
import com.codegym.demo.validations.custom.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private IUserRepository userRepository; // Assuming a Spring Data JPA repository
    public UniqueEmailValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // Handle null/empty emails based on your application's requirements
        }
        return !userRepository.existsByEmail(email); // Custom method in your repository
    }

}