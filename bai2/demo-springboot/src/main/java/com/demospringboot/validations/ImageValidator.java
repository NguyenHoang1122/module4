package com.demospringboot.validations;

import com.demospringboot.validations.custom.Image;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/gif"
    );

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            // Cho phép bỏ trống (nếu muốn bắt buộc phải có ảnh thì return false ở đây)
            return true;
        }
        return ALLOWED_TYPES.contains(file.getContentType());
    }
}
