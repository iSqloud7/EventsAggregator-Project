package mk.ukim.finki.model.dtos.wishlist_dto;

import mk.ukim.finki.model.dtos.event_dto.DisplayEventDTO;
import mk.ukim.finki.model.entities.Wishlist;

import java.util.List;

public record DisplayWishlistDTO(
        Long id,
        Long userId,
        DisplayEventDTO event
) {
    public static DisplayWishlistDTO from(Wishlist wishlist) {
        return new DisplayWishlistDTO(
                wishlist.getId(),
                wishlist.getUser().getId(),
                DisplayEventDTO.from(wishlist.getEvent())
        );
    }

    public static List<DisplayWishlistDTO> from(List<Wishlist> wishlists) {
        return wishlists.stream()
                .map(DisplayWishlistDTO::from)
                .toList();
    }
}