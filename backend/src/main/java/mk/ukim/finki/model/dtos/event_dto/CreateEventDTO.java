package mk.ukim.finki.model.dtos.event_dto;

import mk.ukim.finki.model.entities.Event;

import java.util.List;
import java.util.stream.Collectors;

public record CreateEventDTO(

        String title,
        String image,
        String location,
        String price,
        String timeStart,
        String dateStart,
        String city,
        String description
) {
    public static CreateEventDTO from(Event event) {
        return new CreateEventDTO(
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

    public static List<CreateEventDTO> from(List<Event> events) {
        return events.stream()
                .map(CreateEventDTO::from)
                .collect(Collectors.toList());
    }

    public Event toEvent() {
        return new Event(title, image, location, price, timeStart, dateStart, city, description);
    }
}
