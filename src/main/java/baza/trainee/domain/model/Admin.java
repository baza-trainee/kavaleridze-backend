package baza.trainee.domain.model;


import com.redis.om.spring.annotations.Indexed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class Admin {

    @Id
    @Indexed
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    // Add other fields and methods as needed
}
