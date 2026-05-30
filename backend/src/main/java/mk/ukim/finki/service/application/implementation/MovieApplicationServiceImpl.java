package mk.ukim.finki.service.application.implementation;

import mk.ukim.finki.model.dtos.movie_dto.CreateMovieDTO;
import mk.ukim.finki.model.dtos.movie_dto.DisplayMovieDTO;
import mk.ukim.finki.service.application.MovieApplicationService;
import mk.ukim.finki.service.domain.MovieDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieApplicationServiceImpl implements MovieApplicationService {

    private final MovieDomainService movieDomainService;

    public MovieApplicationServiceImpl(MovieDomainService movieDomainService) {
        this.movieDomainService = movieDomainService;
    }

    @Override
    public Optional<DisplayMovieDTO> create(CreateMovieDTO dto) {
        return this.movieDomainService.create(dto.toMovie())
                .map(DisplayMovieDTO::from);
    }

    @Override
    public Optional<DisplayMovieDTO> update(Long id, CreateMovieDTO dto) {
        return this.movieDomainService.update(id, dto.toMovie())
                .map(DisplayMovieDTO::from);
    }

    @Override
    public void delete(Long id) {
        this.movieDomainService.delete(id);
    }

    @Override
    public List<DisplayMovieDTO> findAll() {
        return DisplayMovieDTO.from(this.movieDomainService.findAll());
    }

    @Override
    public Optional<DisplayMovieDTO> findById(Long id) {
        return this.movieDomainService.findById(id).map(DisplayMovieDTO::from);
    }

    @Override
    public List<DisplayMovieDTO> findByCity(String city) {
        return DisplayMovieDTO.from(this.movieDomainService.findByCity(city));
    }

    @Override
    public List<DisplayMovieDTO> findByDate(String dateStart) {
        return DisplayMovieDTO.from(this.movieDomainService.findByDate(dateStart));
    }

    @Override
    public List<DisplayMovieDTO> findByGenre(String genre) {
        return DisplayMovieDTO.from(this.movieDomainService.findByGenre(genre));
    }

    @Override
    public List<DisplayMovieDTO> filter(String city, String dateStart, String keyword, String genre) {
        return DisplayMovieDTO.from(this.movieDomainService.filter(city, dateStart, keyword, genre));
    }
}