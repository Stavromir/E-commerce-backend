package bg.softuni.Spring.ecommerce.app.model.dto;

import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;

public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRoleEnum userRole;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserDto setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
