package ru.sbercources.filmography.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sbercources.filmography.dto.AddFilmDto;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Order;
import ru.sbercources.filmography.repository.DirectorRepository;
import ru.sbercources.filmography.repository.FilmRepository;
import ru.sbercources.filmography.repository.OrderRepository;

import java.util.List;
import java.util.Set;

@Service
public class DirectorService extends GenericService<Director> {

    private final DirectorRepository repository;
    private final OrderRepository orderRepository;
    private final FilmRepository filmRepository;

    protected DirectorService(DirectorRepository repository, OrderRepository orderRepository,
                              FilmRepository filmRepository) {
        super(repository);
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.filmRepository = filmRepository;
    }

    public void delete(Long id) {
        Set<Order> orders = orderRepository.findAllByDirectorId(id);

        if (orders.size() != 0) {
            return;
        }
        repository.deleteById(id);
        Set<Film> singleDirectorFilms = filmRepository.findAllSingleDirectorFilmsByDirectorId(id);

        for (Film film : singleDirectorFilms) {
            filmRepository.deleteById(film.getId());
        }
    }

    public Director addFilm(AddFilmDto addFilmDto) {
        Director director = repository.findById(addFilmDto.getDirectorId()).orElseThrow();
        Film film = filmRepository.findById(addFilmDto.getFilmId()).orElseThrow();
        director.getFilms().add(film);
        return repository.save(director);
    }

    public Page<Director> searchByDirectorFIO(Pageable pageable, String directorFIO) {
        return repository.findAllByDirectorFIO(pageable, directorFIO);
    }

    public Page<Director> searchByDirectorFIOContaining(Pageable pageable, String directorFIO) {
        return repository.findAllByDirectorFIOContaining(pageable, directorFIO);
    }

    public Page<Director> listAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }
}