package bg.softuni.Spring.ecommerce.app.model.dto;

public class SignupRequestDto {

    private String email;
    private String password;
    private String name;

    public SignupRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public SignupRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

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
