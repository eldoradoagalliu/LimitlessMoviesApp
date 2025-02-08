package com.limitlessmoviesapp.controller;

import com.limitlessmoviesapp.model.User;
import com.limitlessmoviesapp.service.UserService;
import com.limitlessmoviesapp.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public String userDetails(Principal principal, @PathVariable("id") Long userId, Model model) {
        if (userService.principalExists(principal)) return "redirect:/logout";

        User user = userService.searchUser(userId);
        model.addAttribute("user", user);
        return "user_details";
    }

    @PostMapping("/user/{id}")
    public String uploadProfilePicture(@RequestParam("photos") MultipartFile multipartFile,
                                       @PathVariable("id") Long userId) throws IOException {
        User user = userService.searchUser(userId);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
        userService.updateUser(user);

        String uploadDir = "profile-photos/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/user/{id}";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(Principal principal, @PathVariable("id") Long userId, Model model) {
        if (userService.principalExists(principal)) return "redirect:/logout";

        User user = userService.searchUser(userId);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PutMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") Long userId, @Valid @ModelAttribute("user") User user,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "edit_user";
        } else {
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
