package com.limitlessmoviesapp.controllers;

import com.limitlessmoviesapp.models.Cinema;
import com.limitlessmoviesapp.models.Movie;
import com.limitlessmoviesapp.models.Ticket;
import com.limitlessmoviesapp.models.User;
import com.limitlessmoviesapp.services.CinemaService;
import com.limitlessmoviesapp.services.MovieService;
import com.limitlessmoviesapp.services.TicketService;
import com.limitlessmoviesapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CinemaController {
    @Autowired
    private UserService userService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/cinema/{id}")
    public String cinemaDetails(Principal principal, @PathVariable("id") Long cinemaId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        Cinema cinema = cinemaService.getCinema(cinemaId);
        model.addAttribute("cinema", cinema);

        List<Movie> movies = movieService.getCinemaMovies(cinema);
        model.addAttribute("movies", movies);
        return "cinema_details";
    }

    @GetMapping("/admin/cinema/new")
    public String addCinema(Principal principal, @ModelAttribute("cinema") Cinema cinema, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        return "new_cinema";
    }

    @PostMapping("/admin/cinema/new")
    public String addCinema(Principal principal, @Valid @ModelAttribute("cinema") Cinema cinema, BindingResult result,
                           Model model) {
        User cinemaCreator = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", cinemaCreator);
        if(result.hasErrors()) {
            model.addAttribute("cinema", cinema);
            return "new_cinema";
        }
        else {
            Cinema newCinema = new Cinema(cinema.getCinemaName(), cinema.getLatitude(), cinema.getLongitude(),
                                          cinemaCreator);
            //New cinema saved in DB
            cinemaService.updateCinema(newCinema);
            //New cinema added to the list of cinemas
            cinemaCreator.getCinemasCreated().add(newCinema);
            userService.updateUser(cinemaCreator);
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/cinema/edit/{id}")
    public String editCinema(Principal principal, @PathVariable("id") Long cinemaId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        Cinema cinema = cinemaService.getCinema(cinemaId);
        model.addAttribute("cinema", cinema);
        return "edit_cinema";
    }

    @PutMapping("/admin/cinema/edit/{id}")
    public String editCinema(Principal principal, @Valid @ModelAttribute("cinema") Cinema cinema, BindingResult result,
                             @PathVariable("id") Long cinemaId, Model model) {
        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        if(result.hasErrors()) {
            return "edit_cinema";
        }
        else {
            Cinema editedCinema = cinemaService.getCinema(cinemaId);
            //Attributes to be edited
            editedCinema.setCinemaName(cinema.getCinemaName());
            editedCinema.setLatitude(cinema.getLatitude());
            editedCinema.setLongitude(cinema.getLongitude());
            cinemaService.updateCinema(editedCinema);
        }
        return "redirect:/cinema/{id}";
    }

    @DeleteMapping("/admin/cinema/delete/{id}")
    public String deleteCinema(Principal principal, @PathVariable("id") Long cinemaId) {
        //Attributes to be deleted
        Cinema cinema = cinemaService.getCinema(cinemaId);
        List<Movie> movies = movieService.getAllMovies(null);

        for(Movie movie : movies) {
            movie.getCinemas().remove(cinema);
        }
        cinemaService.deleteCinema(cinema);
        return "redirect:/admin";
    }

    @PostMapping("/cinema/book/{id}")
    public String bookTicket(Principal principal, @PathVariable("id") Long movieId) {
        User user = userService.searchUser(principal.getName());
        Movie movie = movieService.getMovie(movieId);
        movie.getViewers().add(user);
        movieService.updateMovie(movie);
        return "redirect:/";
    }

    @PostMapping("cinema/add/movie/{id}")
    public String addMovieToCinema(@PathVariable("id") Long movieId, @Valid @ModelAttribute("ticket") Ticket ticket,
                                   @RequestParam(value = "cinemaId", required = false) Long cinemaId,
                                   @RequestParam(value = "time") String time, @RequestParam(value = "date") String date) {
        Movie movie = movieService.getMovie(movieId);
        Cinema cinema = cinemaService.getCinema(cinemaId);
        movie.getCinemas().add(cinema);
        movieService.updateMovie(movie);

        String dateString = date + " " + time;

        Ticket newTicket = new Ticket(ticket.getPrice(), getDateFromString(dateString), cinema, movie);
        ticketService.updateTicket(newTicket);
        return "redirect:/admin";
    }

    public Date getDateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsed = null;
        try {
            parsed = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date(parsed.getTime());
        return date;
    }
}