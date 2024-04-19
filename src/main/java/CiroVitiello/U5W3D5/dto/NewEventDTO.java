package CiroVitiello.U5W3D5.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(@NotEmpty(message = "the title is required!")
                          @Size(min = 3, max = 15, message = " your title must be  between 3 and 15 characters!")
                          String title,
                          @NotEmpty(message = "the descritpion is required!")
                          @Size(min = 3, max = 50, message = " your description must be  between 3 and 50 characters!")
                          String description,
                          @NotNull(message = "a date is required!")
                          LocalDate date,
                          @NotEmpty(message = "a place is required!")
                          @Size(min = 3, max = 15, message = " your place must be  between 3 and 15 characters!")
                          String place,
                          @Min(value = 1, message = "please insert at least 1 partecipant")
                          int placesAvailable) {
}
