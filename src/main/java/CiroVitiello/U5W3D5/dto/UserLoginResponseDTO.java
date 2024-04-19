package CiroVitiello.U5W3D5.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginResponseDTO(@NotEmpty(message = "Token is required!")
                                   String accessToken) {
}
