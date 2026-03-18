package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCity(String city);

    List<Event> findByDateStart(String dateStart);

    List<Event> findByCityAndDateStart(String city, String dateStart);

    @Query("""
                SELECT event FROM  Event event
                WHERE LOWER(event.title) LIKE  LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Event> searchByTitle(@Param("keyword") String keyword);

    @Query("""
                SELECT event FROM Event event
                WHERE (:city IS NULL OR event.city = :city)
                AND (:dateStart IS NULL OR event.dateStart = :dateStart)
                AND (:keyword IS NULL OR LOWER(event.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    List<Event> filterEvents(
            @Param("city") String city,
            @Param("dateStart") String dateStart,
            @Param("keyword") String keyword
    );
}
