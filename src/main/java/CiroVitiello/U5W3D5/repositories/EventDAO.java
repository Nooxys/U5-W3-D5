package CiroVitiello.U5W3D5.repositories;

import CiroVitiello.U5W3D5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {

    Optional<Event> findByDateAndPlace(LocalDate date, String place);

    Optional<Event> findByTitle(String title);
}
