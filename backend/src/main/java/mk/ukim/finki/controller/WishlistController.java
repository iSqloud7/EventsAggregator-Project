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

    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<DisplayWishlistDTO> addToWishlist(@PathVariable Long userId,
                                                            @PathVariable Long itemId,
                                                            @RequestParam(defaultValue = "EVENT") String type) {
        return wishlistApplicationService.addToWishlist(userId, itemId, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/{itemId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long userId,
                                                   @PathVariable Long itemId,
                                                   @RequestParam(defaultValue = "EVENT") String type) {
        wishlistApplicationService.removeFromWishlist(userId, itemId, type);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/{itemId}/check")
    public ResponseEntity<Boolean> isInWishlist(@PathVariable Long userId,
                                                @PathVariable Long itemId,
                                                @RequestParam(defaultValue = "EVENT") String type) {
        return ResponseEntity.ok(wishlistApplicationService.isInWishlist(userId, itemId, type));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DisplayWishlistDTO>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistApplicationService.findByUser(userId));
    }
}