package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.dto.EventDTO;
import mk.ukim.finki.backend.model.Event;
import mk.ukim.finki.backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    private EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getImage(),
                event.getLocation(),
                event.getPrice(),
                event.getTimeStart(),
                event.getDateStart(),
                event.getCity(),
                event.getDescription()
        );
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        return eventRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public List<EventDTO> getEventsByCity(String city) {
        return eventRepository.findByCity(city)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> searchEvents(String keyword) {
        return eventRepository.searchByTitle(keyword)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}