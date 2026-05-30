package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.movie_dto.CreateMovieDTO;
import mk.ukim.finki.model.dtos.movie_dto.DisplayMovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieApplicationService {

    Optional<DisplayMovieDTO> create(CreateMovieDTO createMovieDTO);

    Optional<DisplayMovieDTO> update(Long id, CreateMovieDTO createMovieDTO);

    void delete(Long id);

    List<DisplayMovieDTO> findAll();

    Optional<DisplayMovieDTO> findById(Long id);

    List<DisplayMovieDTO> findByCity(String city);

    List<DisplayMovieDTO> findByDate(String dateStart);

    List<DisplayMovieDTO> findByGenre(String genre);

    List<DisplayMovieDTO> filter(String city, String dateStart, String keyword, String genre);
}