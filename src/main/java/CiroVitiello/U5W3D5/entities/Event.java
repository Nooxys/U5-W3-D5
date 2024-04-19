package CiroVitiello.U5W3D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
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
    @OneToMany(mappedBy = "events")
    @JsonIgnore
    private List<Reservation> reservations;
}
