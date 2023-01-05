package com.limitlessmovies.controllers;

import com.limitlessmovies.models.User;
import com.limitlessmovies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
public class EmailController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/admin/contact")
    public String showContactForm(Principal principal, Model model) {
        if(userService.principalExists(principal)) return "redirect:/logout";

        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);
        return "contact_form";
    }

    @PostMapping("/admin/contact")
    public String submitContact(HttpServletRequest request,
                                Principal principal, Model model) throws MessagingException, UnsupportedEncodingException {
        User currentUser = userService.searchUser(principal.getName());
        model.addAttribute("currentUser", currentUser);

        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String mailContent = "<p>" + content +"</p>";

        helper.setFrom("leonora.brati@gmail.com","Limitless Movies");
        helper.setTo(new String[]{
                "eldoradoagalliu@gmail.com",
                "behijenezha01@gmail.com",
                "hyrabrat1@gmail.com",
                "eriga.dulla@gmail.com"});

        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
        return "redirect:/admin";
    }
}