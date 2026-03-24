package mk.ukim.finki.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private String price;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "date_start")
    private String dateStart;

    @Column(name = "city")
    private String city;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Event() {
    }

    public Event(String title, String image, String location, String price, String timeStart, String dateStart, String city, String description) {
        this.title = title;
        this.image = image;
        this.location = location;
        this.price = price;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
        this.city = city;
        this.description = description;
    }

    public Event(Long id, String title, String image, String location, String price, String timeStart, String dateStart, String city, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.location = location;
        this.price = price;
        this.timeStart = timeStart;
        this.dateStart = dateStart;
        this.city = city;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}