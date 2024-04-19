package CiroVitiello.U5W3D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String title;
    private String description;
    private LocalDate date;
    private String place;
    private int placesAvailable;
    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Reservation> reservations;

    public Event(String title, String description, LocalDate date, String place, int placesAvailable) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.place = place;
        this.placesAvailable = placesAvailable;
    }
}
