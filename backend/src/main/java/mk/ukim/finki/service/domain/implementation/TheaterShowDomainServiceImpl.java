package mk.ukim.finki.service.domain.implementation;

import jakarta.transaction.Transactional;
import mk.ukim.finki.model.entities.TheaterShow;
import mk.ukim.finki.repository.TheaterShowRepository;
import mk.ukim.finki.repository.WishlistRepository;
import mk.ukim.finki.service.domain.TheaterShowDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterShowDomainServiceImpl implements TheaterShowDomainService {

    private final TheaterShowRepository theaterShowRepository;
    private final WishlistRepository wishlistRepository;

    public TheaterShowDomainServiceImpl(TheaterShowRepository theaterShowRepository, WishlistRepository wishlistRepository) {
        this.theaterShowRepository = theaterShowRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Optional<TheaterShow> create(TheaterShow theaterShow) {
        return Optional.of(this.theaterShowRepository.save(theaterShow));
    }

    @Override
    public Optional<TheaterShow> update(Long id, TheaterShow theaterShow) {
        Optional<TheaterShow> showObj = this.theaterShowRepository.findById(id);

        if (showObj.isPresent()) {
            TheaterShow existingShow = showObj.get();
            existingShow.setTitle(theaterShow.getTitle());
            existingShow.setImage(theaterShow.getImage());
            existingShow.setLocation(theaterShow.getLocation());
            existingShow.setPrice(theaterShow.getPrice());
            existingShow.setTimeStart(theaterShow.getTimeStart());
            existingShow.setDateStart(theaterShow.getDateStart());
            existingShow.setCity(theaterShow.getCity());
            existingShow.setDescription(theaterShow.getDescription());

            return Optional.of(this.theaterShowRepository.save(existingShow));
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        wishlistRepository.deleteByTheaterShowId(id);
        this.theaterShowRepository.deleteById(id);
    }

    @Override
    public List<TheaterShow> findAll() {
        return this.theaterShowRepository.findAll();
    }

    @Override
    public Optional<TheaterShow> findById(Long id) {
        return this.theaterShowRepository.findById(id);
    }

    @Override
    public List<TheaterShow> findByCity(String city) {
        return this.theaterShowRepository.findAll().stream()
                .filter(s -> s.getCity() != null && s.getCity().equalsIgnoreCase(city))
                .toList();
    }

    @Override
    public List<TheaterShow> findByDate(String dateStart) {
        return this.theaterShowRepository.findAll().stream()
                .filter(s -> s.getDateStart() != null && s.getDateStart().equals(dateStart))
                .toList();
    }

    @Override
    public List<TheaterShow> searchByTitle(String keyword) {
        return this.theaterShowRepository.findAll().stream()
                .filter(s -> s.getTitle() != null && s.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }


    @Override
    public List<TheaterShow> filter(String city, String dateStart, String keyword) {
        return this.theaterShowRepository.filterShows(city, dateStart, keyword);
    }
}