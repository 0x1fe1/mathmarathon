package page.pango.mathmarathon.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 1)
    private String password;

    @NotNull
    @Size(min = 1)
    private String email;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;
}