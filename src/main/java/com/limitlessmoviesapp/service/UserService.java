package com.limitlessmoviesapp.service;

import com.limitlessmoviesapp.model.Movie;
import com.limitlessmoviesapp.model.User;
import com.limitlessmoviesapp.repository.RoleRepository;
import com.limitlessmoviesapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName(role));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User searchUser(Long id) {
        return userRepository.findByIdIs(id);
    }

    public User searchUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getViewers(Movie movie) {
        List<User> users = getAllUsers();
        List<User> viewers = new ArrayList<>();
        for (User user : users) {
            if (user.getBookedMovies().contains(movie)) {
                viewers.add(user);
            }
        }
        return viewers;
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    //A method that checks if the princial exists - TO TEST
    public Boolean principalExists(Principal principal) {
        String email = principal.getName();
        User user = searchUser(email);

        if (user == null) {
            return true;
        }
        return false;
    }
}
