package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class SignupRequestDto {

    private String email;
    private String password;
    private String name;


    @Email
    public String getEmail() {
        return email;
    }

    public SignupRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 3, max = 10)
    public String getPassword() {
        return password;
    }

    public SignupRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public SignupRequestDto setName(String name) {
        this.name = name;
        return this;
    }
}
