package mk.ukim.finki.repository;

import mk.ukim.finki.model.entities.Event;
import mk.ukim.finki.model.entities.TheaterShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterShowRepository extends JpaRepository<TheaterShow, Long> {

    List<TheaterShow> findByCity(String city);

    List<TheaterShow> findByDateStart(String dateStart);

    List<TheaterShow> findByCityAndDateStart(String city, String dateStart);

    @Query("""
                SELECT DISTINCT show FROM  TheaterShow show
                WHERE LOWER(show.title) LIKE  LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<TheaterShow> searchByTitle(@Param("keyword") String keyword);

    @Query("""
                SELECT DISTINCT show FROM TheaterShow show
                WHERE (:city IS NULL OR show.city = :city)
                AND (:dateStart IS NULL OR show.dateStart = :dateStart)
                AND (:keyword IS NULL OR LOWER(show.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    List<TheaterShow> filterShows(
            @Param("city") String city,
            @Param("dateStart") String dateStart,
            @Param("keyword") String keyword
    );
}