package ru.sbercources.filmography.rest.controller;

import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.FilmDto;
import ru.sbercources.filmography.mapper.FilmMapper;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.Genre;
import ru.sbercources.filmography.service.FilmService;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/film")
public class FilmController extends GenericController<Film, FilmDto> {

    private final FilmService service;

    protected FilmController(FilmService service, FilmMapper mapper) {
        super(service, mapper);
        this.service = service;
    }

    @GetMapping
    public Set<FilmDto> findAllByTitleOrCountryOrGenre(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Genre genre) {
        return service.findAllByTitleOrCountryOrGenre(title, country, genre)
                .stream().map(super.mapper::toDto)
                .collect(Collectors.toSet());
    }
}