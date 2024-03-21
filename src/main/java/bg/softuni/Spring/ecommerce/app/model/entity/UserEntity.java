package bg.softuni.Spring.ecommerce.app.model.entity;

import bg.softuni.Spring.ecommerce.app.model.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String email;
    private String password;
    private String name;
    private UserRoleEnum role;
    private byte[] img;

    public UserEntity() {
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

    @Lob
    @Column(columnDefinition = "longblob")
    public byte[] getImg() {
        return img;
    }

    public UserEntity setImg(byte[] img) {
        this.img = img;
        return this;
    }
}
