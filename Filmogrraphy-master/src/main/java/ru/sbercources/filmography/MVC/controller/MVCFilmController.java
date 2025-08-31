package ru.sbercources.filmography.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.DirectorDto;
import ru.sbercources.filmography.dto.FilmDto;
import ru.sbercources.filmography.dto.FilmWithDirectorsDto;
import ru.sbercources.filmography.mapper.FilmMapper;
import ru.sbercources.filmography.mapper.FilmWithDirectorsMapper;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.service.FilmService;

import java.util.List;

@Hidden
@Controller
@RequestMapping("/films")
public class MVCFilmController {

    private final FilmMapper mapper;
    private final FilmService service;
    private final FilmWithDirectorsMapper filmWithDirectorsMapper;

    public MVCFilmController(FilmMapper mapper, FilmService service, FilmWithDirectorsMapper filmWithDirectorsMapper) {
        this.mapper = mapper;
        this.service = service;
        this.filmWithDirectorsMapper = filmWithDirectorsMapper;
    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Film> filmPage = service.listAllPaginated(pageRequest);
        List<FilmWithDirectorsDto> filmDtos = filmPage
                .stream()
                .map(filmWithDirectorsMapper::toDto)
                .toList();
        model.addAttribute("films", new PageImpl<>(filmDtos, pageRequest, filmPage.getTotalElements()));
        return "films/viewAllFilms";
    }

    @GetMapping("/add")
    public String create() {
        return "films/addFilm";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("filmForm") FilmDto filmDto) {
        service.create(mapper.toEntity(filmDto));
        return "redirect:/films";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("film", mapper.toDto(service.getOne(id)));
        return "films/updateFilm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("filmForm") FilmDto filmDto) {
        service.update(mapper.toEntity(filmDto));
        return "redirect:/films";
    }

    @PostMapping("/search")
    public String searchByTitle(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model,
            @ModelAttribute("searchFilms") FilmDto filmDto) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Film> filmPage;
        List<FilmWithDirectorsDto> filmWithDirectorsDtos;
        if (filmDto.getTitle().trim().equals("")) {
            filmPage = service.listAllPaginated(pageRequest);
        } else {
            filmPage = service.searchByTitleContaining(pageRequest, filmDto.getTitle());
        }
        filmWithDirectorsDtos = filmPage
                .stream()
                .map(filmWithDirectorsMapper::toDto)
                .toList();
        model.addAttribute("films", new PageImpl<>(filmWithDirectorsDtos, pageRequest, filmPage.getTotalElements()));
        return "films/viewAllFilms";
    }
}