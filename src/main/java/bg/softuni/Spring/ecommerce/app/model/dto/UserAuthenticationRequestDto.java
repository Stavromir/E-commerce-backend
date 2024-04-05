package bg.softuni.Spring.ecommerce.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserAuthenticationRequestDto {

    private String username;
    private String password;


    @Email
    public String getUsername() {
        return username;
    }

    public UserAuthenticationRequestDto setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotNull
    @NotBlank
    public String getPassword() {
        return password;
    }

    public UserAuthenticationRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
