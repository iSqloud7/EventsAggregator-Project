package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.stats_dto.StatsDTO;
import mk.ukim.finki.repository.EventRepository;
import mk.ukim.finki.repository.TheaterShowRepository;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.repository.WishlistRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:5173")
public class StatsController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final TheaterShowRepository theaterShowRepository;

    public StatsController(EventRepository eventRepository,
                           UserRepository userRepository,
                           WishlistRepository wishlistRepository, TheaterShowRepository theaterShowRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.theaterShowRepository = theaterShowRepository;
    }



    @GetMapping
    public StatsDTO getStats(Principal principal) {
        String username = "Guest";
        long totalWishlistsPerUser = 0;

        if (principal != null) {
            username = principal.getName();
            totalWishlistsPerUser = wishlistRepository.countByUserUsername(username);
        }

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

        Map<String, Long> theatersByMonth = theaterShowRepository.findAll()
                .stream()
                .filter(t -> t.getDateStart() != null && t.getDateStart().contains("-"))
                .collect(Collectors.groupingBy(t -> {
                    String[] parts = t.getDateStart().split("-");
                    return parts.length == 3 ? parts[1] : "Unknown";
                }, Collectors.counting()));


        return new StatsDTO(
                eventRepository.count(),
                theaterShowRepository.count(),
                userRepository.count(),
                wishlistRepository.count(),
                totalWishlistsPerUser,
                username,
                eventsByMonth,
                theatersByMonth,
                usersByRole
        );
    }
}