package ru.sbercources.filmography.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class GenericDto {

    private Long id;
    private String createdBy;
    private LocalDateTime createdWhen;
    private String updatedBy;
    private LocalDateTime updatedWhen;
    private boolean isDeleted;
    private LocalDateTime deletedWhen;
    private String deletedBy;
}
