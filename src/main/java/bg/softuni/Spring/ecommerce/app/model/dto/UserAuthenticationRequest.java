package bg.softuni.Spring.ecommerce.app.model.dto;

public class UserAuthenticationRequest {

    private String username;
    private String password;

    public UserAuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public UserAuthenticationRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuthenticationRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
