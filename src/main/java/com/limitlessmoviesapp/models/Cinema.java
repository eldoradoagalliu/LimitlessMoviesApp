package com.limitlessmoviesapp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String cinemaName;

    @NotNull
    @Max(value = 180)
    private Double latitude;

    @NotNull
    @Max(value = 180)
    private Double longitude;

    //Cinema created by Admin
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User cinemaCreator;

    //Movies which will be shown in the cinema
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "cinema_movies",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;

    //Tickets information for movies shown to the cinema
    @Column(updatable=false)
    @OneToMany(mappedBy="cinema", fetch=FetchType.LAZY)
    private List<Ticket> tickets;

    public Cinema() {

    }

    public Cinema(String cinemaName, Double latitude, Double longitude, User cinemaCreator) {
        this.cinemaName = cinemaName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cinemaCreator = cinemaCreator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public User getCinemaCreator() {
        return cinemaCreator;
    }

    public void setCinemaCreator(User cinemaCreator) {
        this.cinemaCreator = cinemaCreator;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}