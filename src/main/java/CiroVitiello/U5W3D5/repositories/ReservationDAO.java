package CiroVitiello.U5W3D5.repositories;

import CiroVitiello.U5W3D5.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {
    
    Optional<Reservation> findByUserIdAndEventId(long userId, long eventId);

    Optional<Reservation> findByIdAndUserId(long id, long userId);

    List<Reservation> findByUserId(long userId);
}
