package com.limitlessmoviesapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * Movie release date in cinema
     */
    private Date dateReleased;

    /**
     * Movie creator, admin role feature
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User movieCreator;

    @Column(updatable = false)
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Comment> movieComments;

    /**
     * Cinemas, where the movie will be displayed
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cinema_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id")
    )
    private List<Cinema> cinemas;

    /**
     * Viewers, which have booked a ticket to watch the movie
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_ticket",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> viewers;

    /**
     * Tickets, which will display information about the price and the display date and time for the movie in the cinema
     */
    @Column(updatable = false)
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Ticket> tickets;
}
