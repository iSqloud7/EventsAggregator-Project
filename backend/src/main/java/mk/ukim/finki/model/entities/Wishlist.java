package mk.ukim.finki.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "WISHLISTS")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "theater_show_id", nullable = true)
    private TheaterShow theaterShow;

    public Wishlist() {}

    public Wishlist(User user, Event event) {
        this.user = user;
        this.event = event;
        this.theaterShow = null;
    }

    public Wishlist(User user, TheaterShow theaterShow) {
        this.user = user;
        this.theaterShow = theaterShow;
        this.event = null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public TheaterShow getTheaterShow() { return theaterShow; }
    public void setTheaterShow(TheaterShow theaterShow) { this.theaterShow = theaterShow; }
}