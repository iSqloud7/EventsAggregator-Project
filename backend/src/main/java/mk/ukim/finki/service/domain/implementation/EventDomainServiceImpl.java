package mk.ukim.finki.service.domain.implementation;

import jakarta.transaction.Transactional;
import mk.ukim.finki.model.entities.Event;
import mk.ukim.finki.repository.EventRepository;
import mk.ukim.finki.repository.WishlistRepository;
import mk.ukim.finki.service.domain.EventDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventDomainServiceImpl implements EventDomainService {

    private final EventRepository eventRepository;
    private final WishlistRepository wishlistRepository;

    public EventDomainServiceImpl(EventRepository eventRepository, WishlistRepository wishlistRepository) {
        this.eventRepository = eventRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Optional<Event> create(Event event) {
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    public Optional<Event> update(Long id, Event event) {
        Optional<Event> event_obj = this.eventRepository.findById(id);

        if (event_obj.isPresent()) {
            Event existing_event = event_obj.get();
            existing_event.setTitle(event.getTitle());
            existing_event.setImage(event.getImage());
            existing_event.setLocation(event.getLocation());
            existing_event.setPrice(event.getPrice());
            existing_event.setTimeStart(event.getTimeStart());
            existing_event.setDateStart(event.getDateStart());
            existing_event.setCity(event.getCity());
            existing_event.setDescription(event.getDescription());

            return Optional.of(this.eventRepository.save(existing_event));
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        wishlistRepository.deleteByEventId(id);
        this.eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public List<Event> findByCity(String city) {
        return this.eventRepository.findByCity(city);
    }

    @Override
    public List<Event> findByDate(String dateStart) {
        return this.eventRepository.findByDateStart(dateStart);
    }

    @Override
    public List<Event> searchByTitle(String keyword) {
        return this.eventRepository.searchByTitle(keyword);
    }

    @Override
    public List<Event> filter(String city, String dateStart, String keyword) {
        return this.eventRepository.filterEvents(city, dateStart, keyword);
    }
}
