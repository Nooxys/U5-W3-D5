package CiroVitiello.U5W3D5.controllers;

import CiroVitiello.U5W3D5.dto.NewEventDTO;
import CiroVitiello.U5W3D5.entities.Event;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService es;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.es.getEvents(page, size, sortBy);
    }

    @GetMapping("/{eventId}")
    public Event findEventsById(@PathVariable long eventId) {
        return es.findById(eventId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.es.save(body);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    public Event findByIdAndUpdate(@PathVariable long eventId, @RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.es.findByIdAndUpdate(eventId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long eventId) {
        this.es.findByIdAndDelete(eventId);
    }

}