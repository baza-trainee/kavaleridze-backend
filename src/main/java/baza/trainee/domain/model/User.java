package baza.trainee.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import baza.trainee.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

@Data
@Document
public class User {

    @Id
    @Indexed
    private String id;

    @NotBlank
    @Email
    @Indexed
    private String email;

    @NotBlank
    private String password;

    @Setter(AccessLevel.PROTECTED)
    private List<String> roles = new ArrayList<>();

    public void addRole(Role role) {
        this.roles.add(role.getValue());
    }
}