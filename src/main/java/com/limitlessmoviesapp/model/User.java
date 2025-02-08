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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 8, max = 128)
    private String password;

    @Transient
    private String confirm;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Column
    private String photos;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
        return "/profile-photos/" + id + "/" + photos;
    }

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    private Date lastLogin;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    /**
     * User roles for sign in
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    /**
     * Movies created by Admin
     */
    @Column(updatable = false)
    @OneToMany(mappedBy = "movieCreator", fetch = FetchType.LAZY)
    private List<Movie> moviesCreated;

    /**
     * Cinemas created by Admin
     */
    @Column(updatable = false)
    @OneToMany(mappedBy = "cinemaCreator", fetch = FetchType.LAZY)
    private List<Cinema> cinemasCreated;

    /**
     * Movies that can be booked in the cinema by users
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_ticket",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> bookedMovies;

    @Column(updatable = false)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> movieComments;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return roles.stream()
                .map(Role::getName)
                .anyMatch(role -> role.equals("ADMIN"));
    }
}
