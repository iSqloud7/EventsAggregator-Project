package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCity(String city);
    List<Event> findByDateStart(String dateStart);
    List<Event> findByCityAndDateStart(String city, String dateStart);

    @Query("SELECT e FROM Event e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Event> searchByTitle(@Param("keyword") String keyword);
}