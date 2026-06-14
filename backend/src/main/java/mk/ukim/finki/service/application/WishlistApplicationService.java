package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.wishlist_dto.DisplayWishlistDTO;

import java.util.List;
import java.util.Optional;

public interface WishlistApplicationService {

    Optional<DisplayWishlistDTO> addToWishlist(Long userId, Long itemId, String type);

    void removeFromWishlist(Long userId, Long itemId, String type);

    List<DisplayWishlistDTO> findByUser(Long userId);

    boolean isInWishlist(Long userId, Long itemId, String type);
}