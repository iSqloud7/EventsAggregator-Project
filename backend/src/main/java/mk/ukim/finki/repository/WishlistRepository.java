package mk.ukim.finki.repository;

import mk.ukim.finki.model.entities.Wishlist;
import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndEvent(User user, Event event);
    boolean existsByUserAndEvent(User user, Event event);
    void deleteByEventId(Long eventId);
}