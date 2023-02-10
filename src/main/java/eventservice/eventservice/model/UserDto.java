package eventservice.eventservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 5, max = 20, message="Username has to be 5-20 characters long")
    @ApiModelProperty(value = "username of account", example = "user123")
    private String username;

    @NotNull
    @Email(message = "Valid email has to be provided")
    @Size(min = 10, max=50, message = "Email has to be 10-50 characters")
    @ApiModelProperty(value = "email of account", example = "user123@gmail.com")
    private String email;

    @NotNull
    @Size(min = 8, max = 20, message="Password has to be 8-20 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "password of account", example = "password1")
    private String password;

    @NotNull
    @Size(min = 3, max = 20, message="Name has to be 3-20 characters long")
    @ApiModelProperty(value = "name of accounts owner", example = "Ivan")
    private String name;

    @NotNull
    @Size(min = 3, max = 20, message="Surname has to be 3-20 characters long")
    @ApiModelProperty(value = "surname of accounts owner", example = "Doer")
    private String surname;

    @ApiModelProperty(value = "role of accounts owner (autogenerated)")
    private RoleDto role;
}