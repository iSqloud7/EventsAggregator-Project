package mk.ukim.finki.backend.service.domain;

import mk.ukim.finki.backend.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventDomainService {

    Optional<Event> create(Event event);

    Optional<Event> update(Long id, Event event);

    void delete(Long id);

    List<Event> findAll();

    Optional<Event> findById(Long id);

    List<Event> findByCity(String city);

    List<Event> findByDate(String dateStart);

    List<Event> searchByTitle(String keyword);

    List<Event> filter(String city, String dateStart, String keyword);
}
