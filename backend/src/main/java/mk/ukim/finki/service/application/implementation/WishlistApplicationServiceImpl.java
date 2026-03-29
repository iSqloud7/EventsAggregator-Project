package mk.ukim.finki.service.application.implementation;

import mk.ukim.finki.model.dtos.wishlist_dto.DisplayWishlistDTO;
import mk.ukim.finki.service.application.WishlistApplicationService;
import mk.ukim.finki.service.domain.WishlistDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistApplicationServiceImpl implements WishlistApplicationService {

    private final WishlistDomainService wishlistDomainService;

    public WishlistApplicationServiceImpl(WishlistDomainService wishlistDomainService) {
        this.wishlistDomainService = wishlistDomainService;
    }

    @Override
    public Optional<DisplayWishlistDTO> addToWishlist(Long userId, Long eventId) {
        return wishlistDomainService.addToWishlist(userId, eventId)
                .map(DisplayWishlistDTO::from);
    }

    @Override
    public void removeFromWishlist(Long userId, Long eventId) {
        wishlistDomainService.removeFromWishlist(userId, eventId);
    }

    @Override
    public List<DisplayWishlistDTO> findByUser(Long userId) {
        return DisplayWishlistDTO.from(wishlistDomainService.findByUser(userId));
    }

    @Override
    public boolean isInWishlist(Long userId, Long eventId) {
        return wishlistDomainService.isInWishlist(userId, eventId);
    }
}