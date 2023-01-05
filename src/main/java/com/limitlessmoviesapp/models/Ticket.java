package com.limitlessmoviesapp.models;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Long price;

    @Future
    private Date cinemaDisplayTime;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;

    public Ticket(){

    }

    public Ticket(Long price, Date cinemaDisplayTime, Cinema cinema, Movie movie) {
        this.price = price;
        this.cinemaDisplayTime = cinemaDisplayTime;
        this.cinema = cinema;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getCinemaDisplayTime() {
        return cinemaDisplayTime;
    }

    public void setCinemaDisplayTime(Date cinemaDisplayTime) {
        this.cinemaDisplayTime = cinemaDisplayTime;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}