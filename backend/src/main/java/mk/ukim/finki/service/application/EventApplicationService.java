package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.event_dto.CreateEventDTO;
import mk.ukim.finki.model.dtos.event_dto.DisplayEventDTO;

import java.util.List;
import java.util.Optional;

public interface EventApplicationService {

    Optional<DisplayEventDTO> create(CreateEventDTO createEventDTO);

    Optional<DisplayEventDTO> update(Long id, CreateEventDTO createEventDTO);

    void delete(Long id);

    List<DisplayEventDTO> findAll();

    Optional<DisplayEventDTO> findById(Long id);

    List<DisplayEventDTO> findByCity(String city);

    List<DisplayEventDTO> findByDate(String dateStart);

    List<DisplayEventDTO> searchByTitle(String keyword);

    List<DisplayEventDTO> filter(String city, String dateStart, String keyword);
}
