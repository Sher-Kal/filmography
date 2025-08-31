package ru.sbercources.filmography.dto;

import lombok.*;
import ru.sbercources.filmography.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RoleDto {


    private Long id;
    private String title;
    private String description;

    public RoleDto (Role role) {
        this.id = role.getId();
        this.title = role.getTitle();
        this.description = role.getDescription();
    }
}