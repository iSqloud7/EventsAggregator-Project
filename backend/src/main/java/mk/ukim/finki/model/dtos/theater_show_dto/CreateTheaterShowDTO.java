package mk.ukim.finki.model.dtos.theater_show_dto;

import mk.ukim.finki.model.entities.TheaterShow;
import java.util.List;
import java.util.stream.Collectors;

public record CreateTheaterShowDTO(
        String title,
        String image,
        String location,
        String price,
        String timeStart,
        String dateStart,
        String city,
        String description
) {
    public static CreateTheaterShowDTO from(TheaterShow show) {
        return new CreateTheaterShowDTO(
                show.getTitle(),
                show.getImage(),
                show.getLocation(),
                show.getPrice(),
                show.getTimeStart(),
                show.getDateStart(),
                show.getCity(),
                show.getDescription()
        );
    }

    public static List<CreateTheaterShowDTO> from(List<TheaterShow> shows) {
        return shows.stream()
                .map(CreateTheaterShowDTO::from)
                .collect(Collectors.toList());
    }

    public TheaterShow toTheaterShow() {
        return new TheaterShow(title, image, location, price, timeStart, dateStart, city, description);
    }
}