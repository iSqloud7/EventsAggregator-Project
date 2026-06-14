package mk.ukim.finki.model.exceptions;

public class TheaterShowNotFoundException extends RuntimeException {
    public TheaterShowNotFoundException(Long id) {
        super("Theater show with id " + id + " not found");
    }
}