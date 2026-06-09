package mk.ukim.finki.service.application.implementation;

import mk.ukim.finki.model.dtos.theater_show_dto.CreateTheaterShowDTO;
import mk.ukim.finki.model.dtos.theater_show_dto.DisplayTheaterShowDTO;
import mk.ukim.finki.model.entities.TheaterShow;
import mk.ukim.finki.service.application.TheaterShowApplicationService;
import mk.ukim.finki.service.domain.TheaterShowDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterShowApplicationServiceImpl implements TheaterShowApplicationService {

    private final TheaterShowDomainService theaterShowDomainService;

    public TheaterShowApplicationServiceImpl(TheaterShowDomainService theaterShowDomainService) {
        this.theaterShowDomainService = theaterShowDomainService;
    }

    @Override
    public Optional<DisplayTheaterShowDTO> create(CreateTheaterShowDTO createTheaterShowDTO) {
        TheaterShow show = createTheaterShowDTO.toTheaterShow();
        return this.theaterShowDomainService.create(show)
                .map(DisplayTheaterShowDTO::from);
    }

    @Override
    public Optional<DisplayTheaterShowDTO> update(Long id, CreateTheaterShowDTO createTheaterShowDTO) {
        TheaterShow show = createTheaterShowDTO.toTheaterShow();
        return this.theaterShowDomainService.update(id, show)
                .map(DisplayTheaterShowDTO::from);
    }

    @Override
    public void delete(Long id) {
        this.theaterShowDomainService.delete(id);
    }

    @Override
    public List<DisplayTheaterShowDTO> findAll() {
        return DisplayTheaterShowDTO.fromList(this.theaterShowDomainService.findAll());
    }

    @Override
    public Optional<DisplayTheaterShowDTO> findById(Long id) {
        return this.theaterShowDomainService.findById(id)
                .map(DisplayTheaterShowDTO::from);
    }

    @Override
    public List<DisplayTheaterShowDTO> findByCity(String city) {
        return DisplayTheaterShowDTO.fromList(this.theaterShowDomainService.findByCity(city));
    }

    @Override
    public List<DisplayTheaterShowDTO> findByDate(String dateStart) {
        return DisplayTheaterShowDTO.fromList(this.theaterShowDomainService.findByDate(dateStart));
    }

    @Override
    public List<DisplayTheaterShowDTO> searchByTitle(String keyword) {
        return DisplayTheaterShowDTO.fromList(this.theaterShowDomainService.searchByTitle(keyword));
    }

    @Override
    public List<DisplayTheaterShowDTO> filter(String city, String dateStart, String keyword) {
        return DisplayTheaterShowDTO.fromList(this.theaterShowDomainService.filter(city, dateStart, keyword));
    }
}