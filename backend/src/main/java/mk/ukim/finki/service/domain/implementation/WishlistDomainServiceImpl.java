package mk.ukim.finki.service.domain.implementation;

import mk.ukim.finki.model.entities.*;
import mk.ukim.finki.model.exceptions.EventNotFoundException;
import mk.ukim.finki.model.exceptions.TheaterShowNotFoundException;
import mk.ukim.finki.model.exceptions.UserNotFoundException;
import mk.ukim.finki.model.exceptions.WishlistAlreadyExistsException;
import mk.ukim.finki.repository.*;
import mk.ukim.finki.service.domain.WishlistDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistDomainServiceImpl implements WishlistDomainService {

    private final WishlistRepository wishlistRepository;
    private final EventRepository eventRepository;
    private final TheaterShowRepository theaterShowRepository;
    private final UserRepository userRepository;

    public WishlistDomainServiceImpl(WishlistRepository wishlistRepository,
                                     EventRepository eventRepository,
                                     TheaterShowRepository theaterShowRepository,
                                     UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.eventRepository = eventRepository;
        this.theaterShowRepository = theaterShowRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<Wishlist> addToWishlist(Long userId, Long itemId, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        if ("THEATER".equalsIgnoreCase(type)) {
            TheaterShow theaterShow = theaterShowRepository.findById(itemId)
                    .orElseThrow(() -> new TheaterShowNotFoundException(itemId));

            if (wishlistRepository.existsByUserAndTheaterShow(user, theaterShow)) {
                throw new WishlistAlreadyExistsException();
            }
            wishlist.setTheaterShow(theaterShow);
            wishlist.setEvent(null);
        } else {
            Event event = eventRepository.findById(itemId)
                    .orElseThrow(() -> new EventNotFoundException(itemId));

            if (wishlistRepository.existsByUserAndEvent(user, event)) {
                throw new WishlistAlreadyExistsException();
            }
            wishlist.setEvent(event);
            wishlist.setTheaterShow(null);
        }

        return Optional.of(wishlistRepository.save(wishlist));
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long userId, Long itemId, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if ("THEATER".equalsIgnoreCase(type)) {
            TheaterShow theaterShow = theaterShowRepository.findById(itemId)
                    .orElseThrow(() -> new TheaterShowNotFoundException(itemId));
            wishlistRepository.findByUserAndTheaterShow(user, theaterShow)
                    .ifPresent(wishlistRepository::delete);
        } else {
            Event event = eventRepository.findById(itemId)
                    .orElseThrow(() -> new EventNotFoundException(itemId));
            wishlistRepository.findByUserAndEvent(user, event)
                    .ifPresent(wishlistRepository::delete);
        }
    }

    @Override
    public List<Wishlist> findByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return wishlistRepository.findByUser(user);
    }

    @Override
    public boolean isInWishlist(Long userId, Long itemId, String type) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return false;

        if ("THEATER".equalsIgnoreCase(type)) {
            TheaterShow theaterShow = theaterShowRepository.findById(itemId).orElse(null);
            return theaterShow != null && wishlistRepository.existsByUserAndTheaterShow(user, theaterShow);
        } else {
            Event event = eventRepository.findById(itemId).orElse(null);
            return event != null && wishlistRepository.existsByUserAndEvent(user, event);
        }
    }
}