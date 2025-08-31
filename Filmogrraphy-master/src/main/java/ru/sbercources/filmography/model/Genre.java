package ru.sbercources.filmography.model;

public enum Genre {

    DETECTIVE("Detective"),
    FANTASTIC("Fantastic"),
    THRILLER("Thriller"),
    COMEDY("Comedy");


    private final String genreText;

    Genre(String genreTExt) {
        this.genreText = genreTExt;
    }

    public String getGenreText() {
        return this.genreText;
    }
}