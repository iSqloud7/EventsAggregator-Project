package mk.ukim.finki.backend.dto.event_dto;

import mk.ukim.finki.backend.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayEventDTO(

        Long id,
        String title,
        String image,
        String location,
        String price,
        String timeStart,
        String dateStart,
        String city,
        String description
) {
    public static DisplayEventDTO from(Event event) {
        return new DisplayEventDTO(
                event.getId(),
                event.getTitle(),
                event.getImage(),
                event.getLocation(),
                event.getPrice(),
                event.getTimeStart(),
                event.getDateStart(),
                event.getCity(),
                event.getDescription()
        );
    }

    public static List<DisplayEventDTO> from(List<Event> events) {
        return events.stream()
                .map(DisplayEventDTO::from)
                .collect(Collectors.toList());
    }

    public Event toEvent() {
        return new Event(id, title, image, location, price, timeStart, dateStart, city, description);
    }
}
