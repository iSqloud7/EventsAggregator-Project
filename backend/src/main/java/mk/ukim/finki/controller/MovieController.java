package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.movie_dto.CreateMovieDTO;
import mk.ukim.finki.model.dtos.movie_dto.DisplayMovieDTO;
import mk.ukim.finki.service.application.MovieApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    private final MovieApplicationService movieApplicationService;

    public MovieController(MovieApplicationService movieApplicationService) {
        this.movieApplicationService = movieApplicationService;
    }

    @GetMapping
    public List<DisplayMovieDTO> getAll() {
        return this.movieApplicationService.findAll();
    }

    @GetMapping("/{id}")
    public DisplayMovieDTO getById(@PathVariable Long id) {
        return this.movieApplicationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found!"));
    }

    @GetMapping("/filter")
    public List<DisplayMovieDTO> filter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre
    ) {
        return this.movieApplicationService.filter(city, dateStart, keyword, genre);
    }

    @PostMapping("/add-movie")
    public DisplayMovieDTO create(@RequestBody CreateMovieDTO createMovieDTO) {
        return this.movieApplicationService.create(createMovieDTO)
                .orElseThrow(() -> new RuntimeException("Movie not created!"));
    }

    @PutMapping("/edit-movie/{id}")
    public DisplayMovieDTO update(@PathVariable Long id,
                                  @RequestBody CreateMovieDTO createMovieDTO) {
        return this.movieApplicationService.update(id, createMovieDTO)
                .orElseThrow(() -> new RuntimeException("Movie not found!"));
    }

    @DeleteMapping("/delete-movie/{id}")
    public void delete(@PathVariable Long id) {
        this.movieApplicationService.delete(id);
    }
}