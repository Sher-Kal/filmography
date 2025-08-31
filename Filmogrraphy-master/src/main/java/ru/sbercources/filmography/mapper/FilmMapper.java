package ru.sbercources.filmography.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sbercources.filmography.dto.FilmDto;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.GenericModel;
import ru.sbercources.filmography.repository.DirectorRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmMapper extends GenericMapper<Film, FilmDto> {

    private final ModelMapper mapper;
    private final DirectorRepository directorRepository;

    protected FilmMapper(ModelMapper mapper, DirectorRepository directorRepository) {
        super(mapper, Film.class, FilmDto.class);
        this.mapper = mapper;
        this.directorRepository = directorRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Film.class, FilmDto.class)
                .addMappings(m -> m.skip(FilmDto::setDirectorsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(FilmDto.class, Film.class)
                .addMappings(m -> m.skip(Film::setDirectors)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(FilmDto source, Film destination) {
        if(!Objects.isNull(source.getDirectorsIds())) {
            destination.setDirectors(directorRepository.findAllByIdIn(source.getDirectorsIds()));
        } else {
            destination.setDirectors(null);
        }
    }

    @Override
    void mapSpecificFields(Film source, FilmDto destination) {
        destination.setDirectorsIds(getIds(source));
    }

    private Set<Long> getIds(Film film) {
        return Objects.isNull(film) || Objects.isNull(film.getId())
                ? null
                : film.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}