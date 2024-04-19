package CiroVitiello.U5W3D5.services;

import CiroVitiello.U5W3D5.dto.NewEventDTO;
import CiroVitiello.U5W3D5.entities.Event;
import CiroVitiello.U5W3D5.exceptions.BadRequestException;
import CiroVitiello.U5W3D5.exceptions.NotFoundException;
import CiroVitiello.U5W3D5.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventDAO ed;

    public Page<Event> getEvents(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ed.findAll(pageable);
    }

    public Event save(NewEventDTO body) {
        this.ed.findByDateAndPlace(body.date(), body.place())
                .ifPresent(event -> {
                    throw new BadRequestException("There is another event in " + body.place() + " at " + body.date());
                });
        this.ed.findByTitle(body.title())
                .ifPresent(event -> {
                    throw new BadRequestException("There is another event called " + body.title());
                });

        Event newEvent = new Event(body.title(), body.description(), body.date(), body.place(), body.placesAvailable());
        return this.ed.save(newEvent);
    }

    public Event findById(long id) {
        return this.ed.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event findByIdAndUpdate(long id, NewEventDTO body) {
        Event found = this.findById(id);
        this.ed.findByDateAndPlace(body.date(), body.place())
                .ifPresent(event -> {
                    throw new BadRequestException("There is another event in " + body.place() + " at " + body.date());
                });
        this.ed.findByTitle(body.title())
                .ifPresent(event -> {
                    throw new BadRequestException("There is another event called " + body.title());
                });
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setDate(body.date());
        found.setPlace(body.place());
        found.setPlacesAvailable(body.placesAvailable());
        return this.ed.save(found);
    }

    public void findByIdAndDelete(long id) {
        Event found = this.findById(id);
        this.ed.delete(found);
    }
}
