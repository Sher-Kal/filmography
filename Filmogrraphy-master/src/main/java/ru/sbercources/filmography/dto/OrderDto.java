package ru.sbercources.filmography.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends GenericDto {

    private Long userId;
    private Long filmId;
    private LocalDateTime rentDate;
    private Integer rentPeriod;
    private boolean isPurchased;
    private FilmDto filmDto;
    private UserDto userDto;
}