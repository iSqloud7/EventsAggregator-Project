package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.theater_show_dto.CreateTheaterShowDTO;
import mk.ukim.finki.model.dtos.theater_show_dto.DisplayTheaterShowDTO;
import mk.ukim.finki.service.application.TheaterShowApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater-shows")
@CrossOrigin(origins = "http://localhost:5173")
public class TheaterShowController {

    private final TheaterShowApplicationService theaterShowApplicationService;

    public TheaterShowController(TheaterShowApplicationService theaterShowApplicationService) {
        this.theaterShowApplicationService = theaterShowApplicationService;
    }

    @GetMapping
    public List<DisplayTheaterShowDTO> getAll() {
        return this.theaterShowApplicationService.findAll();
    }

    @GetMapping("/{id}")
    public DisplayTheaterShowDTO getById(@PathVariable Long id) {
        return this.theaterShowApplicationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater Show not found!"));
    }

    @GetMapping("/filter")
    public List<DisplayTheaterShowDTO> filter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String keyword
    ) {
        return this.theaterShowApplicationService.filter(city, dateStart, keyword);
    }

    @PostMapping("/add-show")
    public DisplayTheaterShowDTO create(@RequestBody CreateTheaterShowDTO createTheaterShowDTO) {
        return this.theaterShowApplicationService.create(createTheaterShowDTO)
                .orElseThrow(() -> new RuntimeException("Theater Show not created!"));
    }

    @PutMapping("/edit-show/{id}")
    public DisplayTheaterShowDTO update(
            @PathVariable Long id,
            @RequestBody CreateTheaterShowDTO createTheaterShowDTO) {
        return this.theaterShowApplicationService.update(id, createTheaterShowDTO)
                .orElseThrow(() -> new RuntimeException("Theater Show not found!"));
    }

    @DeleteMapping("/delete-show/{id}")
    public void delete(@PathVariable Long id){
        this.theaterShowApplicationService.delete(id);
    }
}