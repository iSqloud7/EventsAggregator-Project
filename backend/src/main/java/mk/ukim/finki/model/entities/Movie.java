package mk.ukim.finki.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "genre")
    private String genre;

    @Column(name = "duration")
    private String duration;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "date_start")
    private String dateStart;

    @Column(name = "location")
    private String location;

    @Column(name = "city")
    private String city;

    @Column(name = "price")
    private String price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Movie() {}

    public Movie(String title, String image, String genre, String duration,
                 String timeStart, String dateStart, String location,
                 String city, String price, String description) {
        this.title = title;
        this.image = image;
        this.genre = genre;
        this.duration = duration;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
        this.location = location;
        this.city = city;
        this.price = price;
        this.description = description;
    }

    public Movie(Long id, String title, String image, String genre, String duration,
                 String timeStart, String dateStart, String location,
                 String city, String price, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.genre = genre;
        this.duration = duration;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
        this.location = location;
        this.city = city;
        this.price = price;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getTimeStart() { return timeStart; }
    public void setTimeStart(String timeStart) { this.timeStart = timeStart; }
    public String getDateStart() { return dateStart; }
    public void setDateStart(String dateStart) { this.dateStart = dateStart; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}