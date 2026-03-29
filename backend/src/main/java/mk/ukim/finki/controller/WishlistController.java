package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.wishlist_dto.DisplayWishlistDTO;
import mk.ukim.finki.service.application.WishlistApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    private final WishlistApplicationService wishlistApplicationService;

    public WishlistController(WishlistApplicationService wishlistApplicationService) {
        this.wishlistApplicationService = wishlistApplicationService;
    }

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<DisplayWishlistDTO> addToWishlist(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return wishlistApplicationService.addToWishlist(userId, eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        wishlistApplicationService.removeFromWishlist(userId, eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DisplayWishlistDTO>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistApplicationService.findByUser(userId));
    }

    @GetMapping("/{userId}/{eventId}/check")
    public ResponseEntity<Boolean> isInWishlist(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        return ResponseEntity.ok(wishlistApplicationService.isInWishlist(userId, eventId));
    }
}