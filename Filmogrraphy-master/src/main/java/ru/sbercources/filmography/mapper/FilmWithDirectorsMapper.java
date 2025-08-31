package ru.sbercources.filmography.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sbercources.filmography.dto.FilmWithDirectorsDto;
import ru.sbercources.filmography.model.Film;
import ru.sbercources.filmography.model.GenericModel;
import ru.sbercources.filmography.repository.DirectorRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmWithDirectorsMapper extends GenericMapper<Film, FilmWithDirectorsDto> {

    private final ModelMapper mapper;
    private final DirectorRepository directorRepository;

    protected FilmWithDirectorsMapper(ModelMapper mapper, DirectorRepository directorRepository) {
        super(mapper, Film.class, FilmWithDirectorsDto.class);
        this.mapper = mapper;
        this.directorRepository = directorRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Film.class, FilmWithDirectorsDto.class)
                .addMappings(m -> m.skip(FilmWithDirectorsDto::setDirectorsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(FilmWithDirectorsDto.class, Film.class)
                .addMappings(m -> m.skip(Film::setDirectors)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(FilmWithDirectorsDto source, Film destination) {
        destination.setDirectors(directorRepository.findAllByIdIn(source.getDirectorsIds()));
    }

    @Override
    void mapSpecificFields(Film source, FilmWithDirectorsDto destination) {
        destination.setDirectorsIds(getIds(source));
    }

    private Set<Long> getIds(Film Film) {
        return Objects.isNull(Film) || Objects.isNull(Film.getId())
                ? null
                : Film.getDirectors().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}