package CiroVitiello.U5W3D5.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewReservationDTO(@NotNull(message = " please insert the ID of the event")
                                @Min(value = 1, message = "the ID must be at least 1")
                                long eventId) {
}
