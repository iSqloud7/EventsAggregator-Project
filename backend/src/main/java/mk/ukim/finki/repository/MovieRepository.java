package mk.ukim.finki.repository;

import mk.ukim.finki.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByCity(String city);

    List<Movie> findByDateStart(String dateStart);

    List<Movie> findByGenreContainingIgnoreCase(String genre);

    @Query("""
            SELECT m FROM Movie m
            WHERE (:city IS NULL OR m.city = :city)
            AND (:dateStart IS NULL OR m.dateStart = :dateStart)
            AND (:keyword IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:genre IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%')))
            """)
    List<Movie> filterMovies(
            @Param("city") String city,
            @Param("dateStart") String dateStart,
            @Param("keyword") String keyword,
            @Param("genre") String genre
    );
}