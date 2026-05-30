package mk.ukim.finki.service.domain.implementation;

import mk.ukim.finki.model.entities.Movie;
import mk.ukim.finki.repository.MovieRepository;
import mk.ukim.finki.service.domain.MovieDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieDomainServiceImpl implements MovieDomainService {

    private final MovieRepository movieRepository;

    public MovieDomainServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Movie> create(Movie movie) {
        return Optional.of(this.movieRepository.save(movie));
    }

    @Override
    public Optional<Movie> update(Long id, Movie movie) {
        return this.movieRepository.findById(id).map(existing -> {
            existing.setTitle(movie.getTitle());
            existing.setImage(movie.getImage());
            existing.setGenre(movie.getGenre());
            existing.setDuration(movie.getDuration());
            existing.setTimeStart(movie.getTimeStart());
            existing.setDateStart(movie.getDateStart());
            existing.setLocation(movie.getLocation());
            existing.setCity(movie.getCity());
            existing.setPrice(movie.getPrice());
            existing.setDescription(movie.getDescription());
            return this.movieRepository.save(existing);
        });
    }

    @Override
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return this.movieRepository.findById(id);
    }

    @Override
    public List<Movie> findByCity(String city) {
        return this.movieRepository.findByCity(city);
    }

    @Override
    public List<Movie> findByDate(String dateStart) {
        return this.movieRepository.findByDateStart(dateStart);
    }

    @Override
    public List<Movie> findByGenre(String genre) {
        return this.movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    @Override
    public List<Movie> filter(String city, String dateStart, String keyword, String genre) {
        return this.movieRepository.filterMovies(city, dateStart, keyword, genre);
    }
}