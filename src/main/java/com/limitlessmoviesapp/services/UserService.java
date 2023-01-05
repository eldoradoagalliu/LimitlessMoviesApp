package com.limitlessmoviesapp.services;

import com.limitlessmoviesapp.models.Movie;
import com.limitlessmoviesapp.models.User;
import com.limitlessmoviesapp.repositories.RoleRepository;
import com.limitlessmoviesapp.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder bCryptPwEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder bCryptPwEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.bCryptPwEncoder = bCryptPwEncoder;
    }

    public void createUser(User user, String role) {
        user.setPassword(bCryptPwEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByName(role));
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User searchUser(Long id) {
        return userRepo.findByIdIs(id);
    }

    public User searchUser(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> getViewers(Movie movie) {
        List<User> users = getAllUsers();
        List<User> viewers = new ArrayList<>();
        for(User user : users) {
            if(user.getBookedMovies().contains(movie)) {
                viewers.add(user);
            }
        }
        return viewers;
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    //A method that checks if the princial exists - TO TEST
    public Boolean principalExists(Principal principal) {
        String email = principal.getName();
        User user = searchUser(email);

        if(user == null) {
            return true;
        }
        return false;
    }
}