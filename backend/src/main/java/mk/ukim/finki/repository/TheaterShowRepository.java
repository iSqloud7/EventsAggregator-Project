package mk.ukim.finki.repository;

import mk.ukim.finki.model.entities.TheaterShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterShowRepository extends JpaRepository<TheaterShow, Long> {

    @Query("""
                SELECT t FROM TheaterShow t
                WHERE (:city IS NULL OR t.city = :city)
                AND (:dateStart IS NULL OR t.dateStart = :dateStart)
                AND (:keyword IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    List<TheaterShow> filterShows(
            @Param("city") String city,
            @Param("dateStart") String dateStart,
            @Param("keyword") String keyword
    );

}