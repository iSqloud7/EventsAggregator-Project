package mk.ukim.finki.model.dtos.stats_dto;

import java.util.Map;

public record StatsDTO(
        long totalEvents,
        long totalTheaterShows,
        long totalUsers,
        long totalWishlists,
        long totalWishlistsPerUser,
        String username,
        Map<String, Long> eventsByMonth,
        Map<String, Long> theatersByMonth,
        Map<String, Long> usersByRole
) {}
