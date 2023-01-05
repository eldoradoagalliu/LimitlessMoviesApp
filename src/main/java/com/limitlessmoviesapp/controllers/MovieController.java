package com.limitlessmoviesapp.controllers;

import com.limitlessmoviesapp.models.*;
import com.limitlessmoviesapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/movie/{id}")
    public String movieDetails(Principal principal, @ModelAttribute("comment") Comment comment,
                               @PathVariable("id") Long movieId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        Movie movie = movieService.getMovie(movieId);
        model.addAttribute("movie", movie);
        //Cinemas that will show this movie
        List<Cinema> cinemas = cinemaService.getMovieCinemas(movie);
        model.addAttribute("cinemas", cinemas);
        //Movie commets from users
        List<Comment> comments = commentService.getMovieComments(movieId);
        model.addAttribute("comments", comments);
        return "movie_details";
    }

    @PostMapping("/movie/comment/{id}")
    public String addComment(Principal principal, @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult result, @PathVariable("id") Long movieId, Model model) {
        Movie movie = movieService.getMovie(movieId);
        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        if(result.hasErrors()) {
            model.addAttribute("movie", movie);
            model.addAttribute("cinemas", cinemaService.getMovieCinemas(movie));
            model.addAttribute("comments", commentService.getMovieComments(movieId));
            return "movie_details";
        }
        else {
            Comment newComment = new Comment(comment.getContent());
            newComment.setUser(userService.searchUser(principal.getName()));
            newComment.setMovie(movie);
            commentService.updateComment(newComment);
            return "redirect:/movie/{id}";
        }
    }

    @GetMapping("/admin/movie/new")
    public String addMovie(Principal principal, @ModelAttribute("movie") Movie movie, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        return "new_movie";
    }

    @PostMapping("/admin/movie/new")
    public String addMovie(Principal principal, @Valid @ModelAttribute("movie") Movie movie, BindingResult result,
                           Model model) {
        User movieCreator = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", movieCreator);
        if(result.hasErrors()) {
            model.addAttribute("movie", movie);
            return "new_movie";
        }
        else {
            Movie newMovie = new Movie(movie.getTitle(), movie.getGenre(), movie.getDateReleased(),
                                       movieCreator);
            //New movie saved in DB
            movieService.updateMovie(newMovie);
            //New movie added to the list of created movies
            movieCreator.getMoviesCreated().add(newMovie);
            userService.updateUser(movieCreator);
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/movie/edit/{id}")
    public String editMovie(Principal principal, @PathVariable("id") Long movieId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        Movie movie = movieService.getMovie(movieId);
        model.addAttribute("movie", movie);
        return "edit_movie";
    }

    @PutMapping("/admin/movie/edit/{id}")
    public String editMovie(Principal principal, @Valid @ModelAttribute("movie") Movie movie, BindingResult result,
                            @PathVariable("id") Long movieId, Model model) {
        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        if(result.hasErrors()) {
            return "edit_movie";
        }
        else {
            Movie editedMovie = movieService.getMovie(movieId);
            editedMovie.setTitle(movie.getTitle());
            editedMovie.setGenre(movie.getGenre());
            movieService.updateMovie(editedMovie);
        }
        return "redirect:/movie/{id}";
    }

    @DeleteMapping("/admin/movie/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long movieId) {
        //Attributes to be deleted
        Movie movie = movieService.getMovie(movieId);
        List<User> viewers = userService.getViewers(movie);
        List<Comment> comments = commentService.getMovieComments(movieId);
        List<Cinema> cinemas = cinemaService.getMovieCinemas(movie);

        for(Comment comment : comments) {
            commentService.deleteComment(comment);
        }
        for(Cinema cinema : cinemas) {
            cinema.getMovies().remove(movie);
        }
        for(User viewer : viewers) {
            viewer.getBookedMovies().remove(movie);
        }
        movieService.deleteMovie(movie);
        return "redirect:/admin";
    }
}