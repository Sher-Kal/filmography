package ru.sbercources.filmography.dto;

import lombok.*;
import ru.sbercources.filmography.model.Genre;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto extends GenericDto {

    private String title;
    private Integer premierYear;
    private String country;
    private Genre genre;
    private Set<Long> directorsIds;
}