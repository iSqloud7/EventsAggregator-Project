package mk.ukim.finki.model.dtos.stats_dto;

import java.util.Map;

public record StatsDTO(
        long totalEvents,
        long totalUsers,
        long totalWishlists,
        Map<String, Long> eventsByMonth,
        Map<String, Long> usersByRole
) {}
