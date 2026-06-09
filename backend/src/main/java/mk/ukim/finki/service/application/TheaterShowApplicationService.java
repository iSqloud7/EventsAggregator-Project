package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.theater_show_dto.CreateTheaterShowDTO;
import mk.ukim.finki.model.dtos.theater_show_dto.DisplayTheaterShowDTO;

import java.util.List;
import java.util.Optional;

public interface TheaterShowApplicationService {
    Optional<DisplayTheaterShowDTO> create(CreateTheaterShowDTO createTheaterShowDTO);
    Optional<DisplayTheaterShowDTO> update(Long id, CreateTheaterShowDTO createTheaterShowDTO);
    void delete(Long id);
    List<DisplayTheaterShowDTO> findAll();
    Optional<DisplayTheaterShowDTO> findById(Long id);
    List<DisplayTheaterShowDTO> findByCity(String city);
    List<DisplayTheaterShowDTO> findByDate(String dateStart);
    List<DisplayTheaterShowDTO> searchByTitle(String keyword);
    List<DisplayTheaterShowDTO> filter(String city, String dateStart, String keyword);
}