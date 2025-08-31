package ru.sbercources.filmography.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto extends GenericDto {

    @NotBlank(message = "Поле не должно быть пустым")
    private String directorFIO;
    @NotBlank(message = "Поле не должно быть пустым")
    private String position;
    private Set<Long> filmsIds;
}