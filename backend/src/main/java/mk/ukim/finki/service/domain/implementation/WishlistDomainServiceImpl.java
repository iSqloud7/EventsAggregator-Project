package mk.ukim.finki.service.domain.implementation;

import mk.ukim.finki.model.entities.Event;
import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.entities.Wishlist;
import mk.ukim.finki.model.exceptions.EventNotFoundException;
import mk.ukim.finki.model.exceptions.UserNotFoundException;
import mk.ukim.finki.model.exceptions.WishlistAlreadyExistsException;
import mk.ukim.finki.repository.EventRepository;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.repository.WishlistRepository;
import mk.ukim.finki.service.domain.WishlistDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistDomainServiceImpl implements WishlistDomainService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public WishlistDomainServiceImpl(WishlistRepository wishlistRepository,
                                     UserRepository userRepository,
                                     EventRepository eventRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Wishlist> addToWishlist(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (wishlistRepository.existsByUserAndEvent(user, event)) {
            throw new WishlistAlreadyExistsException();
        }

        Wishlist wishlist = new Wishlist(user, event);
        return Optional.of(wishlistRepository.save(wishlist));
    }

    @Override
    public void removeFromWishlist(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        wishlistRepository.findByUserAndEvent(user, event)
                .ifPresent(wishlistRepository::delete);
    }

    @Override
    public List<Wishlist> findByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return wishlistRepository.findByUser(user);
    }

    @Override
    public boolean isInWishlist(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        return wishlistRepository.existsByUserAndEvent(user, event);
    }

}