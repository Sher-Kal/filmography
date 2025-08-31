package ru.sbercources.filmography.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "books_seq", allocationSize = 1)
public class Film extends GenericModel {

    @Column(name = "title")
    private String title;

    @Column(name = "premier_year")
    private Integer premierYear;

    @Column(name = "country")
    private String country;

    @Enumerated
    @Column(name = "genre")
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "film_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            foreignKey = @ForeignKey(name = "FK_FILMS_DIRECTORS"),
            inverseJoinColumns = @JoinColumn(name = "director_id"),
            inverseForeignKey = @ForeignKey(name = "FK_DIRECTORS_FILMS")
    )
    private Set<Director> directors;

    @Builder
    public Film(Long id, LocalDateTime createdWhen, String createdBy, LocalDateTime updatedWhen, String updatedBy, LocalDateTime deletedWhen, String deletedBy, boolean isDeleted, String title, Integer premierYear    , String country, Genre genre, Set<Director> directors) {
        super(id, createdWhen, createdBy, updatedWhen, updatedBy, deletedWhen, deletedBy, isDeleted);
        this.title = title;
        this.premierYear = premierYear;
        this.country = country;
        this.genre = genre;
        this.directors = directors;
    }
}