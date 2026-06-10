package mk.ukim.finki.service.domain;

import mk.ukim.finki.model.entities.TheaterShow;
import java.util.List;
import java.util.Optional;

public interface TheaterShowDomainService {

    Optional<TheaterShow> create(TheaterShow theaterShow);

    Optional<TheaterShow> update(Long id, TheaterShow theaterShow);

    void delete(Long id);

    List<TheaterShow> findAll();

    Optional<TheaterShow> findById(Long id);

    List<TheaterShow> findByCity(String city);

    List<TheaterShow> findByDate(String dateStart);

    List<TheaterShow> searchByTitle(String keyword);

    List<TheaterShow> filter(String city, String dateStart, String keyword);
}