package mk.ukim.finki.service.application.implementation;

import mk.ukim.finki.model.dtos.event_dto.CreateEventDTO;
import mk.ukim.finki.model.dtos.event_dto.DisplayEventDTO;
import mk.ukim.finki.model.entities.Event;
import mk.ukim.finki.service.application.EventApplicationService;
import mk.ukim.finki.service.domain.EventDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventApplicationServiceImpl implements EventApplicationService {

    private final EventDomainService eventDomainService;

    public EventApplicationServiceImpl(EventDomainService eventDomainService) {
        this.eventDomainService = eventDomainService;
    }

    @Override
    public Optional<DisplayEventDTO> create(CreateEventDTO createEventDTO) {
        Event event = createEventDTO.toEvent();

        return this.eventDomainService.create(event)
                .map(DisplayEventDTO::from);
    }

    @Override
    public Optional<DisplayEventDTO> update(Long id, CreateEventDTO createEventDTO) {
        Event event = createEventDTO.toEvent();

        return this.eventDomainService.update(id, event)
                .map(DisplayEventDTO::from);
    }

    @Override
    public void delete(Long id) {
        this.eventDomainService.delete(id);
    }

    @Override
    public List<DisplayEventDTO> findAll() {
        return DisplayEventDTO.from(this.eventDomainService.findAll());
    }

    @Override
    public Optional<DisplayEventDTO> findById(Long id) {
        return this.eventDomainService.findById(id)
                .map(DisplayEventDTO::from);
    }

    @Override
    public List<DisplayEventDTO> findByCity(String city) {
        return DisplayEventDTO.from(this.eventDomainService.findByCity(city));
    }

    @Override
    public List<DisplayEventDTO> findByDate(String dateStart) {
        return DisplayEventDTO.from(this.eventDomainService.findByDate(dateStart));
    }

    @Override
    public List<DisplayEventDTO> searchByTitle(String keyword) {
        return DisplayEventDTO.from(this.eventDomainService.searchByTitle(keyword));
    }

    @Override
    public List<DisplayEventDTO> filter(String city, String dateStart, String keyword) {
        return DisplayEventDTO.from(this.eventDomainService.filter(city, dateStart, keyword));
    }
}
