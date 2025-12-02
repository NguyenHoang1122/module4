package com.formvalidationregistration.model;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class User implements Validator {

//    @NotBlank(message = "Firstname không được để trống")
//    @Size(min = 5, max = 45, message = "Firstname phải từ 5 đến 45 ký tự")
    private String firstname;

//    @NotBlank(message = "Lastname không được để trống")
//    @Size(min = 5, max = 45, message = "Lastname phải từ 5 đến 45 ký tự")
    private String lastname;

//    @Pattern(regexp = "^(\\+84|0)\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String phonenumber;

//    @Min(value = 18, message = "Tuổi phải lớn hơn hoặc bằng 18")
    private int age;

//    @Email(message = "Email không hợp lệ")
//    @NotBlank(message = "Email không được để trống")
    private String email;

    public User() {
    }

    public User(String firstname, String lastname, String phonenumber, int age, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.age = age;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    //validation
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Validate firstname
        if (user.getFirstname() == null || user.getFirstname().isEmpty()) {
            errors.rejectValue("firstname", "firstname.empty");
        } else if (user.getFirstname().length() < 5 || user.getFirstname().length() > 45) {
            errors.rejectValue("firstname", "firstname.size");
        }

        // Validate lastname
        if (user.getLastname() == null || user.getLastname().isEmpty()) {
            errors.rejectValue("lastname", "lastname.empty");
        } else if (user.getLastname().length() < 5 || user.getLastname().length() > 45) {
            errors.rejectValue("lastname", "lastname.size");
        }

        // Validate phonenumber
        if (user.getPhonenumber() == null || !user.getPhonenumber().matches("^(\\+84|0)\\d{9}$")) {
            errors.rejectValue("phonenumber", "phonenumber.invalid");
        }

        // Validate age
        if (user.getAge() < 18) {
            errors.rejectValue("age", "age.min");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.empty");
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.invalid");
        }
    }

}
