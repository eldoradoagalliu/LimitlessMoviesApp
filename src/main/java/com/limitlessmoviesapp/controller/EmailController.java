package com.limitlessmoviesapp.controller;

import com.limitlessmoviesapp.model.User;
import com.limitlessmoviesapp.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final UserService userService;
    private final JavaMailSender mailSender;

    @GetMapping("/admin/contact")
    public String showContactForm(Principal principal, Model model) {
        if (userService.principalExists(principal)) return "redirect:/logout";

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

        String mailContent = "<p>" + content + "</p>";

        helper.setFrom("Personal/Business Email Address", "Limitless Movies");
        helper.setTo(new String[]{
                "Add Users Email Addresses"});

        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
        return "redirect:/admin";
    }
}
