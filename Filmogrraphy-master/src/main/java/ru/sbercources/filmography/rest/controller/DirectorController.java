package ru.sbercources.filmography.rest.controller;

import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.AddFilmDto;
import ru.sbercources.filmography.dto.DirectorDto;
import ru.sbercources.filmography.dto.DirectorWithFilmsDto;
import ru.sbercources.filmography.mapper.DirectorMapper;
import ru.sbercources.filmography.mapper.DirectorWithFilmsMapper;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.service.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/rest/director")
public class DirectorController extends GenericController<Director, DirectorDto> {

    private final DirectorService service;
    private final DirectorWithFilmsMapper directorWithFilmsMapper;

    protected DirectorController(
            DirectorService service,
            DirectorMapper mapper,
            DirectorWithFilmsMapper directorWithFilmsMapper) {
        super(service, mapper);
        this.service = service;
        this.directorWithFilmsMapper = directorWithFilmsMapper;
    }

    @PostMapping("/add-director")
    public DirectorDto addFilm(@RequestBody AddFilmDto addFilmDto) {
        return directorWithFilmsMapper.toDto(service.addFilm(addFilmDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/director-films")
    public List<DirectorWithFilmsDto> getDirectorsWithFilms() {
        return service.listAll().stream().map(directorWithFilmsMapper::toDto).toList();
    }
}