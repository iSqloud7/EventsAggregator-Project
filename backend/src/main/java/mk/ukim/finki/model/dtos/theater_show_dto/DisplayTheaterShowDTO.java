package mk.ukim.finki.model.dtos.theater_show_dto;

import mk.ukim.finki.model.entities.TheaterShow;
import java.util.List;

public record DisplayTheaterShowDTO(
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
    public static DisplayTheaterShowDTO from(TheaterShow show) {
        return new DisplayTheaterShowDTO(
                show.getId(),
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

    public static List<DisplayTheaterShowDTO> fromList(List<TheaterShow> shows) {
        return shows.stream().map(DisplayTheaterShowDTO::from).toList();
    }
}