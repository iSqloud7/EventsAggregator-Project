package mk.ukim.finki.model.dtos.wishlist_dto;

import mk.ukim.finki.model.entities.Wishlist;
import java.util.List;

public record DisplayWishlistDTO(
        Long id,
        Long userId,
        Long itemId,
        String title,
        String image,
        String type
) {
    public static DisplayWishlistDTO from(Wishlist wishlist) {
        if (wishlist.getEvent() != null) {
            return new DisplayWishlistDTO(
                    wishlist.getId(),
                    wishlist.getUser().getId(),
                    wishlist.getEvent().getId(),
                    wishlist.getEvent().getTitle(),
                    wishlist.getEvent().getImage(),
                    "EVENT"
            );
        }

        if (wishlist.getTheaterShow() != null) {
            return new DisplayWishlistDTO(
                    wishlist.getId(),
                    wishlist.getUser().getId(),
                    wishlist.getTheaterShow().getId(),
                    wishlist.getTheaterShow().getTitle(),
                    wishlist.getTheaterShow().getImage(),
                    "THEATER"
            );
        }

        return new DisplayWishlistDTO(wishlist.getId(), wishlist.getUser().getId(), null, "Unknown Item", null, "UNKNOWN");
    }

    public static List<DisplayWishlistDTO> from(List<Wishlist> wishlists) {
        return wishlists.stream()
                .map(DisplayWishlistDTO::from)
                .toList();
    }
}