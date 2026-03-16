package mk.ukim.finki.backend.controller;

import mk.ukim.finki.backend.dto.EventDTO;
import mk.ukim.finki.backend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
    @GetMapping("/city/{city}")
    public List<EventDTO> getEventsByCity(@PathVariable String city) {
        return eventService.getEventsByCity(city);
    }
    @GetMapping("/search")
    public List<EventDTO> searchEvents(@RequestParam String keyword) {
        return eventService.searchEvents(keyword);
    }
}