package mk.ukim.finki.model.dtos.movie_dto;

import mk.ukim.finki.model.entities.Movie;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayMovieDTO(
        Long id,
        String title,
        String image,
        String genre,
        String duration,
        String timeStart,
        String dateStart,
        String location,
        String city,
        String price,
        String description
) {
    public static DisplayMovieDTO from(Movie movie) {
        return new DisplayMovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getImage(),
                movie.getGenre(),
                movie.getDuration(),
                movie.getTimeStart(),
                movie.getDateStart(),
                movie.getLocation(),
                movie.getCity(),
                movie.getPrice(),
                movie.getDescription()
        );
    }

    public static List<DisplayMovieDTO> from(List<Movie> movies) {
        return movies.stream().map(DisplayMovieDTO::from).collect(Collectors.toList());
    }

    public Movie toMovie() {
        return new Movie(id, title, image, genre, duration, timeStart,
                dateStart, location, city, price, description);
    }
}