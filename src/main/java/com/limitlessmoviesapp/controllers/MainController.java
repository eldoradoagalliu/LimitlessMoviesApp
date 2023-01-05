package com.limitlessmoviesapp.controllers;

import com.limitlessmoviesapp.models.Cinema;
import com.limitlessmoviesapp.models.Movie;
import com.limitlessmoviesapp.models.Ticket;
import com.limitlessmoviesapp.models.User;
import com.limitlessmoviesapp.services.CinemaService;
import com.limitlessmoviesapp.services.MovieService;
import com.limitlessmoviesapp.services.UserService;
import com.limitlessmoviesapp.validation.UserValidator;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @RequestMapping("/")
    public String home(Principal principal, Model model, @Param("keyword") String keyword) throws UnirestException {
        userService.principalExists(principal);

        User user = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", user);

        if (user != null) {
            user.setLastLogin(new Date());
            userService.updateUser(user);
            // Admin User will be redirected to the Admin Page
            if (user.getRoles().get(0).getName().contains("ROLE_ADMIN")) {
                return "redirect:/admin";
            }
        }
        //Normal Users will be redirected to Home Page
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        model.addAttribute("cinemas", cinemas);

        List<Movie> movies = movieService.getAllMovies( keyword);
        model.addAttribute("movies", movies);
        model.addAttribute("keyword", keyword);

        //Input personal API Key!!!
        //API for action movies -
        String apiUrl = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=action";

        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get(apiUrl).asJson();

        JSONObject object = jsonNodeHttpResponse.getBody().getObject();

        JSONArray jsonArray = object.getJSONArray("Search");
        ArrayList<JSONObject> results = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray.length(); i++) {
            results.add(jsonArray.getJSONObject(i));
        }
        model.addAttribute("resultsAction", results);

        //API for horror movies
        String apiUrl2 = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=horror";

        HttpResponse<JsonNode> jsonNodeHttpResponse2 = Unirest.get(apiUrl2).asJson();

        JSONObject object2 = jsonNodeHttpResponse2.getBody().getObject();

        JSONArray jsonArray2 = object2.getJSONArray("Search");
        ArrayList<JSONObject> results2 = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray2.length(); i++) {
            results2.add(jsonArray2.getJSONObject(i));
        }
        model.addAttribute("resultsHorror", results2);

        //API for comedy movies
        String apiUrl3 = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=comedy";

        HttpResponse<JsonNode> jsonNodeHttpResponse3 = Unirest.get(apiUrl3).asJson();

        JSONObject object3 = jsonNodeHttpResponse3.getBody().getObject();

        JSONArray jsonArray3 = object3.getJSONArray("Search");
        ArrayList<JSONObject> results3 = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray3.length(); i++) {
            results3.add(jsonArray3.getJSONObject(i));
        }
        model.addAttribute("resultsComedy", results3);

        //API for thriller movies
        String apiUrl4 = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=thriller";

        HttpResponse<JsonNode> jsonNodeHttpResponse4 = Unirest.get(apiUrl4).asJson();

        JSONObject object4 = jsonNodeHttpResponse4.getBody().getObject();

        JSONArray jsonArray4 = object4.getJSONArray("Search");
        ArrayList<JSONObject> results4 = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray4.length(); i++) {
            results4.add(jsonArray4.getJSONObject(i));
        }
        model.addAttribute("resultsThriller", results4);

        //API for drama movies
        String apiUrl5 = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=drama";

        HttpResponse<JsonNode> jsonNodeHttpResponse5 = Unirest.get(apiUrl5).asJson();

        JSONObject object5 = jsonNodeHttpResponse5.getBody().getObject();

        JSONArray jsonArray5 = object5.getJSONArray("Search");
        ArrayList<JSONObject> results5 = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray5.length(); i++) {
            results5.add(jsonArray5.getJSONObject(i));
        }
        model.addAttribute("resultsDrama", results5);

        //API for anime movies
        String apiUrl6 = "https://www.omdbapi.com/?i=tt3896198&apikey=448f5075&s=anime";

        HttpResponse<JsonNode> jsonNodeHttpResponse6 = Unirest.get(apiUrl6).asJson();

        JSONObject object6 = jsonNodeHttpResponse6.getBody().getObject();

        JSONArray jsonArray6 = object6.getJSONArray("Search");
        ArrayList<JSONObject> results6 = new ArrayList<JSONObject>();

        for(int i=0; i<jsonArray6.length(); i++) {
            results6.add(jsonArray6.getJSONObject(i));
        }
        model.addAttribute("resultsAnime", results6);

        return "home";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {
        userValidator.validate(user, result);

        String password = user.getPassword();
        if (result.hasErrors()) {
            return "register";
        }

        // First User will be made Admin
        if (userService.getAllUsers().size() == 0) {
            userService.createUser(user, "ROLE_ADMIN");
        }
        else {
            userService.createUser(user, "ROLE_USER");
        }

        authWithHttpServletRequest(request, user.getEmail(), password);
        return "redirect:/";
    }

    //This method will authenticate the newly registered users
    public void authWithHttpServletRequest(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (ServletException e) {
            System.out.println("Error while login: " + e);
        }
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    //Admin
    @RequestMapping("/admin")
    public String adminPage(Principal principal, @ModelAttribute("ticket") Ticket ticket, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        String email = principal.getName();
        model.addAttribute("currentUser", userService.searchUser(email));
        model.addAttribute("cinemas", cinemaService.getAllCinemas());
        model.addAttribute("movies", movieService.getAllMovies(null));
        model.addAttribute("allMovies", movieService.getAllMovies(null));
        model.addAttribute("allCinemas", cinemaService.getAllCinemas());
        return "admin";
    }
}