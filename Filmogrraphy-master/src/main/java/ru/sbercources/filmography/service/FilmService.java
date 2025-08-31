package ru.sbercources.filmography.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Genre;
import ru.sbercources.filmography.repository.DirectorRepository;
import ru.sbercources.filmography.repository.FilmRepository;

import java.util.Set;


@Service
public class FilmService extends GenericService<Film> {

    private final FilmRepository repository;
    private final DirectorRepository directorRepository;

    public FilmService(FilmRepository repository, DirectorRepository directorRepository) {
        super(repository);
        this.repository = repository;
        this.directorRepository = directorRepository;
    }


    public Set<Film> findAllByTitleOrCountryOrGenre(String title, String country, Genre genre) {
        return repository.findAllByTitleOrCountryOrGenre(title, country, genre);
    }

    public Page<Film> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Film> searchByTitleContaining(Pageable pageable, String title) {
        return repository.findAllByTitleContaining(pageable, title);
    }
}