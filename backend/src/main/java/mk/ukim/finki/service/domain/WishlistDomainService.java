package mk.ukim.finki.service.domain;

import mk.ukim.finki.model.entities.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistDomainService {
    Optional<Wishlist> addToWishlist(Long userId, Long eventId);
    void removeFromWishlist(Long userId, Long eventId);
    List<Wishlist> findByUser(Long userId);
    boolean isInWishlist(Long userId, Long eventId);
}