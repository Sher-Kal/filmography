package ru.sbercources.filmography.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Order;

import java.util.Set;

@Repository
public interface OrderRepository extends GenericRepository<Order> {

    @Query(nativeQuery = true, value = """
                SELECT *
                FROM films
                WHERE id IN
                    (SELECT DISTINCT film_id
                    FROM orders
                    WHERE user_id =:userId
                    )
            """)
    Set<Film> findAllFilmsByUserId(@Param(value = "userId") Long userId);

    @Query(nativeQuery = true, value = """
        SELECT *
        FROM orders
        WHERE film_id IN (SELECT DISTINCT film_id
                          FROM film_directors
                          WHERE director_id =:directorId)
    """)
    Set<Order> findAllByDirectorId(@Param(value = "directorId") Long directorId);
}