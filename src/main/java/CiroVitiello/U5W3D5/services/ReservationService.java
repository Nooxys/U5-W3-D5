package CiroVitiello.U5W3D5.services;

import CiroVitiello.U5W3D5.dto.NewReservationDTO;
import CiroVitiello.U5W3D5.entities.Reservation;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.exceptions.NotFoundException;
import CiroVitiello.U5W3D5.repositories.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO rd;

    @Autowired
    private UserService us;

    @Autowired
    private EventService es;


    public List<Reservation> getReservations(long userId) {
        return this.rd.findByUserId(userId);
    }

    public Reservation save(NewReservationDTO body, long userId) {

        this.rd.findByUserIdAndEventId(userId, body.eventId())
                .ifPresent(reservation -> {
                    throw new BadRequestException("You already have a reservation for this event!");
                });

        if (this.es.findById(body.eventId()).getReservations().size() < this.es.findById(body.eventId()).getPlacesAvailable()) {
            Reservation newReservation = new Reservation(us.findById(userId), es.findById(body.eventId()));
            return this.rd.save(newReservation);
        } else throw new BadRequestException("Sorry, The event is full, you cannot take a reservation for this event");

    }

    public Reservation findById(long id, long userId) {
        return this.rd.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id, long userId) {
        Reservation found = this.findById(id, userId);
        this.rd.delete(found);
    }
}
