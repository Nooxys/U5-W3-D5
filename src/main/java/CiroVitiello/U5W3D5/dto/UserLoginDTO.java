package CiroVitiello.U5W3D5.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(@NotEmpty(message = "Email is required! ")
                           String email,
                           @NotEmpty(message = "Password is required!")
                           String password) {
}
