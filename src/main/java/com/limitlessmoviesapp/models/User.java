package com.limitlessmovies.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

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

    @Column(nullable = true)
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

    //User roles for sign in
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    //Movies created by Admin
    @Column(updatable=false)
    @OneToMany(mappedBy="movieCreator", fetch=FetchType.LAZY)
    private List<Movie> moviesCreated;

    //Cinemas created by Admin
    @Column(updatable=false)
    @OneToMany(mappedBy="cinemaCreator", fetch=FetchType.LAZY)
    private List<Cinema> cinemasCreated;

    //Movies to be booked in the cinema by users
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "book_ticket",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> bookedMovies;

    //Movies comments
    @Column(updatable=false)
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private List<Comment> movieComments;

    public User(){

    }

    public User(String firstName, String lastName, String email, Date birthdate, String photos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Movie> getMoviesCreated() {
        return moviesCreated;
    }

    public void setMoviesCreated(List<Movie> moviesCreated) {
        this.moviesCreated = moviesCreated;
    }

    public List<Cinema> getCinemasCreated() {
        return cinemasCreated;
    }

    public void setCinemasCreated(List<Cinema> cinemasCreated) {
        this.cinemasCreated = cinemasCreated;
    }

    public List<Movie> getBookedMovies() {
        return bookedMovies;
    }

    public void setBookedMovies(List<Movie> bookedMovies) {
        this.bookedMovies = bookedMovies;
    }

    public List<Comment> getMovieComments() {
        return movieComments;
    }

    public void setMovieComments(List<Comment> movieComments) {
        this.movieComments = movieComments;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}