package com.limitlessmoviesapp.controllers;

import com.limitlessmoviesapp.models.User;
import com.limitlessmoviesapp.services.UserService;
import com.limitlessmoviesapp.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public String userDetails(Principal principal, @PathVariable("id") Long userId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User user = userService.searchUser(userId);
        model.addAttribute("user", user);
        return "user_details";
    }

    @PostMapping("/user/{id}")
    public String uploadProfilePicture(@RequestParam("photos") MultipartFile multipartFile,
                                       @PathVariable("id") Long userId )throws IOException {
        User user = userService.searchUser(userId);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        userService.updateUser(user);

        String uploadDir = "profile-photos/" + user.getId() ;
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/user/{id}";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User user = userService.searchUser(userId);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PutMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") Long userId, @Valid @ModelAttribute("user") User user,
                           BindingResult result ) {
        if (result.hasErrors()) {
            return "edit_user";
        }
        else {
            User editedUser = userService.searchUser(userId);
            editedUser.setFirstName(user.getFirstName());
            editedUser.setLastName(user.getLastName());
            editedUser.setEmail(user.getEmail());
            editedUser.setBirthdate(user.getBirthdate());
            userService.updateUser(editedUser);
            return "redirect:/user/{id}";
        }
    }
}