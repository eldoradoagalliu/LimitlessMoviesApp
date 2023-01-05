package com.limitlessmovies.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String title;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String genre;
    //Date released in cinema
    private Date dateReleased;

    //Admin creating a movie
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User movieCreator;

    //Movies comments
    @Column(updatable=false)
    @OneToMany(mappedBy="movie", fetch=FetchType.LAZY)
    private List<Comment> movieComments;

    //Movies which will be shown to the cinema
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "cinema_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id")
    )
    private List<Cinema> cinemas;

    //Viewers which will view the movie in the cinema
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "book_ticket",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> viewers;

    //Tickets which will display information about the price
    // and the display date and time for the movie in the cinema
    @Column(updatable=false)
    @OneToMany(mappedBy="movie", fetch=FetchType.LAZY)
    private List<Ticket> tickets;

    public Movie() {

    }

    public Movie(String title, String genre, Date dateReleased, User movieCreator) {
        this.title = title;
        this.genre = genre;
        this.dateReleased = dateReleased;
        this.movieCreator = movieCreator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    public User getMovieCreator() {
        return movieCreator;
    }

    public void setMovieCreator(User movieCreator) {
        this.movieCreator = movieCreator;
    }

    public List<Comment> getMovieComments() {
        return movieComments;
    }

    public void setMovieComments(List<Comment> movieComments) {
        this.movieComments = movieComments;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public List<User> getViewers() {
        return viewers;
    }

    public void setViewers(List<User> viewers) {
        this.viewers = viewers;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}