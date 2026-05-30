package mk.ukim.finki.service.domain;

import mk.ukim.finki.model.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDomainService {

    Optional<Movie> create(Movie movie);

    Optional<Movie> update(Long id, Movie movie);

    void delete(Long id);

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    List<Movie> findByCity(String city);

    List<Movie> findByDate(String dateStart);

    List<Movie> findByGenre(String genre);

    List<Movie> filter(String city, String dateStart, String keyword, String genre);
}