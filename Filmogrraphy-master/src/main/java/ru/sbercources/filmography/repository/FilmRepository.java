package ru.sbercources.filmography.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Genre;

import java.util.Set;

@Repository
public interface FilmRepository extends GenericRepository<Film> {

    Set<Film> findAllByTitleOrCountryOrGenre(String title, String country, Genre genre);

    //получить все фильмы по id участника, где этот участник является единственным
    @Query(nativeQuery = true, value = """
        SELECT DISTINCT *
        FROM films
        WHERE id IN (SELECT film_id
                     FROM film_directors
                     WHERE
                             film_id IN (SELECT film_id
                                         FROM
                                             (SELECT film_id, COUNT(film_id) count
                                              FROM film_directors fd
                                              GROUP BY film_id) t1
                                         WHERE t1.count = 1)
                       AND director_id =:directorId)
    """)
    Set<Film> findAllSingleDirectorFilmsByDirectorId(@Param(value = "directorId")Long directorId);

    Set<Film> findAllByIdIn(Set<Long> ids);

    Page<Film> findAllByTitleContaining(Pageable pageable, String title);
}