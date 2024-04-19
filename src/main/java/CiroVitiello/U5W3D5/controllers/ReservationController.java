package CiroVitiello.U5W3D5.controllers;

import CiroVitiello.U5W3D5.dto.NewReservationDTO;
import CiroVitiello.U5W3D5.entities.Reservation;
import CiroVitiello.U5W3D5.entities.User;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService rs;

    @GetMapping
    public List<Reservation> getAllReservations(@AuthenticationPrincipal User currentUser) {
        return this.rs.getReservations(currentUser.getId());
    }

    @GetMapping("/{reservationId}")
    public Reservation findReservationById(@PathVariable long reservationId, @AuthenticationPrincipal User currentUser) {
        return rs.findById(reservationId, currentUser.getId());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@RequestBody @Validated NewReservationDTO body, BindingResult validation, @AuthenticationPrincipal User currentUser) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.rs.save(body, currentUser.getId());
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long reservationId, @AuthenticationPrincipal User currentUser) {
        this.rs.findByIdAndDelete(reservationId, currentUser.getId());
    }

}
