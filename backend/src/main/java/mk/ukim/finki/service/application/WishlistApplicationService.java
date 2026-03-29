package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.wishlist_dto.DisplayWishlistDTO;

import java.util.List;
import java.util.Optional;

public interface WishlistApplicationService {
    Optional<DisplayWishlistDTO> addToWishlist(Long userId, Long eventId);
    void removeFromWishlist(Long userId, Long eventId);
    List<DisplayWishlistDTO> findByUser(Long userId);
    boolean isInWishlist(Long userId, Long eventId);
}