package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.stats_dto.StatsDTO;
import mk.ukim.finki.repository.EventRepository;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.repository.WishlistRepository;
import org.springframework.web.bind.annotation.*;
import mk.ukim.finki.model.entities.Event;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:5173")
public class StatsController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;

    public StatsController(EventRepository eventRepository,
                           UserRepository userRepository,
                           WishlistRepository wishlistRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping
    public StatsDTO getStats() {

        Map<String, Long> usersByRole = userRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(u -> u.getRole().name(), Collectors.counting()));

        Map<String, Long> eventsByMonth = eventRepository.findAll()
                .stream()
                .filter(e -> e.getDateStart() != null && e.getDateStart().contains("-"))
                .collect(Collectors.groupingBy(e -> {
                    String[] parts = e.getDateStart().split("-");
                    return parts.length == 3 ? parts[1] : "Unknown";
                }, Collectors.counting()));

        return new StatsDTO(
                eventRepository.count(),
                userRepository.count(),
                wishlistRepository.count(),
                eventsByMonth,
                usersByRole
        );
    }
}