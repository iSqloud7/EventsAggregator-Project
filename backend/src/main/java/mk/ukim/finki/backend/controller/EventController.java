package mk.ukim.finki.backend.controller;

import mk.ukim.finki.backend.dto.event_dto.CreateEventDTO;
import mk.ukim.finki.backend.dto.event_dto.DisplayEventDTO;
import mk.ukim.finki.backend.service.application.EventApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {

    private final EventApplicationService eventApplicationService;

    public EventController(EventApplicationService eventApplicationService) {
        this.eventApplicationService = eventApplicationService;
    }

    @GetMapping
    public List<DisplayEventDTO> getAll() {
        return this.eventApplicationService.findAll();
    }

    @GetMapping("/{id}")
    public DisplayEventDTO getById(@PathVariable Long id) {
        return this.eventApplicationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
    }

    public List<DisplayEventDTO> filter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String keyword
    ) {
        return this.eventApplicationService.filter(city, dateStart, keyword);
    }

    @PostMapping("/add-event")
    public DisplayEventDTO create(@RequestBody CreateEventDTO createEventDTO) {

        return this.eventApplicationService.create(createEventDTO)
                .orElseThrow(() -> new RuntimeException("Event not created!"));
    }

    @PutMapping("/edit-event/{id}")
    public DisplayEventDTO update(
            @PathVariable Long id,
            @RequestBody CreateEventDTO createEventDTO) {

        return this.eventApplicationService.update(id, createEventDTO)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
    }

    @DeleteMapping("/delete-event/{id}")
    public void delete(@PathVariable Long id){
        this.eventApplicationService.delete(id);
    }
}
