package bg.softuni.Spring.ecommerce.app.model.dto;

public class UserAuthenticationRequestDto {

    private String username;
    private String password;

    public UserAuthenticationRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public UserAuthenticationRequestDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuthenticationRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
