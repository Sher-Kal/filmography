package ru.sbercources.filmography.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.sbercources.filmography.dto.LoginDto;
import ru.sbercources.filmography.dto.UserDto;
import ru.sbercources.filmography.mapper.FilmWithDirectorsMapper;
import ru.sbercources.filmography.mapper.UserMapper;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.User;
import ru.sbercources.filmography.security.JwtTokenUtil;
import ru.sbercources.filmography.service.UserService;
import ru.sbercources.filmography.service.userDetails.CustomUserDetailsService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/rest/user")
public class UserController extends GenericController<User, UserDto> {

    private final UserService service;
    private final FilmWithDirectorsMapper filmMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    protected UserController(UserService service, UserMapper mapper, FilmWithDirectorsMapper filmMapper, CustomUserDetailsService customUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        super(service, mapper);
        this.service = service;
        this.filmMapper = filmMapper;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //Список всех арендованных/купленных фильмов у пользователя
    @GetMapping("/{userId}/userFilms")
    public Set<Film> getUserFilms(@PathVariable Long userId) {
        return service.getAllFilmsByUserId(userId);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
        Map<String, Object> response = new HashMap<>();

        if (!service.checkPassword(loginDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user!\nWrong password.");
        }

        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDto.getLogin());
        String token = jwtTokenUtil.generateToken(foundUser);

        response.put("token", token);
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }
}