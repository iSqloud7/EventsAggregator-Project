package mk.ukim.finki.backend.dto;

public class EventDTO {

    private Long id;
    private String title;
    private String image;
    private String location;
    private String price;
    private String timeStart;
    private String dateStart;
    private String city;
    private String description;

    public EventDTO(Long id, String title, String image, String location,
                    String price, String timeStart, String dateStart,
                    String city, String description) {
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

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
    public String getLocation() { return location; }
    public String getPrice() { return price; }
    public String getTimeStart() { return timeStart; }
    public String getDateStart() { return dateStart; }
    public String getCity() { return city; }
    public String getDescription() { return description; }
}