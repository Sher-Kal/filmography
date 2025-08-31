package ru.sbercources.filmography.MVC.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.AddFilmDto;
import ru.sbercources.filmography.dto.DirectorDto;
import ru.sbercources.filmography.mapper.DirectorMapper;
import ru.sbercources.filmography.mapper.FilmMapper;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.service.DirectorService;
import ru.sbercources.filmography.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/directors")
public class MVCDirectorController {

    private final DirectorService service;
    private final FilmService filmService;
    private final DirectorMapper mapper;
    private final FilmMapper filmMapper;

    public MVCDirectorController(DirectorService service, FilmService filmService, DirectorMapper mapper, FilmMapper filmMapper) {
        this.service = service;
        this.filmService = filmService;
        this.mapper = mapper;
        this.filmMapper = filmMapper;
    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorFIO"));
        Page<Director> directorPage = service.listAllPaginated(pageRequest);
        List<DirectorDto> directorDtos = directorPage
                .stream()
                .map(mapper::toDto)
                .toList();
        model.addAttribute("directors", new PageImpl<>(directorDtos, pageRequest, directorPage.getTotalElements()));
        return "directors/viewAllDirectors";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("director", mapper.toDto(service.getOne(id)));
        return "directors/updateDirector";
    }

    @GetMapping("/add")
    public String create(@ModelAttribute("directorForm") DirectorDto directorDto) {
        return "directors/addDirector";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("directorForm") @Valid DirectorDto directorDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/directors/addDirector";
        } else {
            service.create(mapper.toEntity(directorDto));
            return "redirect:/directors";
        }
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("directorForm") DirectorDto directorDto) {
        service.update(mapper.toEntity(directorDto));
        return "redirect:/directors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/directors";
    }

    @PostMapping("/search")
    public String searchByDirectorFIO(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model,
            @ModelAttribute("searchDirectors") DirectorDto directorDto) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "directorFIO"));
        Page<Director> directorPage;
        List<DirectorDto> directorDtos;
        if (directorDto.getDirectorFIO().trim().equals("")) {
            directorPage = service.listAllPaginated(pageRequest);
        } else {
            directorPage = service.searchByDirectorFIOContaining(pageRequest, directorDto.getDirectorFIO());
        }
        directorDtos = directorPage
                .stream()
                .map(mapper::toDto)
                .toList();
        model.addAttribute("directors", new PageImpl<>(directorDtos, pageRequest, directorPage.getTotalElements()));
        return "directors/viewAllDirectors";
    }

    @GetMapping("/add-director/{directorId}")
    public String addFilm(Model model, @PathVariable Long directorId) {
        model.addAttribute("films", filmMapper.toDtos(filmService.listAll()));
        model.addAttribute("directorId", directorId);
        model.addAttribute("director", mapper.toDto(service.getOne(directorId)).getDirectorFIO());
        return "directors/addDirectorFilm";
    }

    @PostMapping("/add-director")
    public String addFilm(@ModelAttribute("directorFilmForm") AddFilmDto addFilmDto) {
        service.addFilm(addFilmDto);
        return "redirect:/directors";
    }
}