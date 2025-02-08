package com.limitlessmoviesapp.controller;

import com.limitlessmoviesapp.model.Cinema;
import com.limitlessmoviesapp.model.Movie;
import com.limitlessmoviesapp.model.Ticket;
import com.limitlessmoviesapp.model.User;
import com.limitlessmoviesapp.service.CinemaService;
import com.limitlessmoviesapp.service.MovieService;
import com.limitlessmoviesapp.service.TicketService;
import com.limitlessmoviesapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CinemaController {

    private final UserService userService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final TicketService ticketService;

    @GetMapping("/cinema/{id}")
    public String cinemaDetails(Principal principal, @PathVariable("id") Long cinemaId, Model model) {
        if (userService.principalExists(principal)) return "redirect:/logout";

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
        if (userService.principalExists(principal)) return "redirect:/logout";
        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        return "new_cinema";
    }

    @PostMapping("/admin/cinema/new")
    public String addCinema(Principal principal, @Valid @ModelAttribute("cinema") Cinema cinema, BindingResult result,
                            Model model) {
        User cinemaCreator = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", cinemaCreator);
        if (result.hasErrors()) {
            model.addAttribute("cinema", cinema);
            return "new_cinema";
        } else {
            Cinema newCinema = Cinema.builder()
                    .cinemaName(cinema.getCinemaName())
                    .latitude(cinema.getLatitude())
                    .longitude(cinema.getLongitude())
                    .cinemaCreator(cinemaCreator)
                    .build();
            // New cinema saved in DB
            cinemaService.updateCinema(newCinema);
            // New cinema added to the list of cinemas
            cinemaCreator.getCinemasCreated().add(newCinema);
            userService.updateUser(cinemaCreator);
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/cinema/edit/{id}")
    public String editCinema(Principal principal, @PathVariable("id") Long cinemaId, Model model) {
        if (userService.principalExists(principal)) return "redirect:/logout";

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
        if (result.hasErrors()) {
            return "edit_cinema";
        } else {
            Cinema editedCinema = cinemaService.getCinema(cinemaId);
            // Attributes to be edited
            editedCinema.setCinemaName(cinema.getCinemaName());
            editedCinema.setLatitude(cinema.getLatitude());
            editedCinema.setLongitude(cinema.getLongitude());
            cinemaService.updateCinema(editedCinema);
        }
        return "redirect:/cinema/{id}";
    }

    @DeleteMapping("/admin/cinema/delete/{id}")
    public String deleteCinema(@PathVariable("id") Long cinemaId) {
        // Attributes to be deleted
        Cinema cinema = cinemaService.getCinema(cinemaId);
        List<Movie> movies = movieService.getAllMovies(null);

        for (Movie movie : movies) {
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

        Ticket newTicket = Ticket.builder()
                .price(ticket.getPrice())
                .cinemaDisplayTime(getDateFromString(dateString))
                .cinema(cinema)
                .movie(movie)
                .build();
        ticketService.updateTicket(newTicket);
        return "redirect:/admin";
    }

    public Date getDateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long time = 0;
        Date parsed;
        try {
            parsed = format.parse(dateString);
            time = parsed.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(time);
    }
}
