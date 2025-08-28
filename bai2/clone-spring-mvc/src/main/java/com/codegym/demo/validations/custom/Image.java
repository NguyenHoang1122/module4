package com.codegym.demo.validations.custom;

import com.codegym.demo.validations.ImageValidator;
import com.codegym.demo.validations.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface Image {
    String message() default "Invalid image format (allowed: jpg, jpeg, png, gif)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}