package ru.sbercources.filmography.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sbercources.filmography.dto.DirectorDto;
import ru.sbercources.filmography.model.Director;
import ru.sbercources.filmography.model.GenericModel;
import ru.sbercources.filmography.repository.FilmRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DirectorMapper extends GenericMapper<Director, DirectorDto> {

    private final ModelMapper mapper;
    private final FilmRepository filmRepository;

    protected DirectorMapper(ModelMapper mapper, FilmRepository filmRepository) {
        super(mapper, Director.class, DirectorDto.class);
        this.mapper = mapper;
        this.filmRepository = filmRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Director.class, DirectorDto.class)
                .addMappings(m -> m.skip(DirectorDto::setFilmsIds)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DirectorDto.class, Director.class)
                .addMappings(m -> m.skip(Director::setFilms)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(DirectorDto source, Director destination) {
        if (!Objects.isNull(source.getFilmsIds())) {
            destination.setFilms(filmRepository.findAllByIdIn(source.getFilmsIds()));
        } else {
            destination.setFilms(null);
        }
    }

    @Override
    void mapSpecificFields(Director source, DirectorDto destination) {
        destination.setFilmsIds(getIds(source));
    }

    private Set<Long> getIds(Director director) {
        return Objects.isNull(director) || Objects.isNull(director.getId())
                ? null
                : director.getFilms().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}