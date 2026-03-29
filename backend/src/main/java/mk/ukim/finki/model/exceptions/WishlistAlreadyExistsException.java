package mk.ukim.finki.model.exceptions;

public class WishlistAlreadyExistsException extends RuntimeException {
    public WishlistAlreadyExistsException() {
        super("Event is already in wishlist");
    }
}