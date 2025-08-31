package ru.sbercources.filmography.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sbercources.filmography.dto.UserDto;
import ru.sbercources.filmography.model.User;

@Component
public class UserMapper extends GenericMapper<User, UserDto> {

    protected UserMapper(ModelMapper mapper) {
        super(mapper, User.class, UserDto.class);
    }
}