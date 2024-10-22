package com.it.controller;

import com.it.dto.request.AuthDto;
import com.it.exception.AppException;
import com.it.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authDto", new AuthDto());
        return "page/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthDto authDto, HttpSession session, Model model) {
        try {
            userService.login(authDto);
            session.setAttribute("username", authDto.getUsername());
        } catch (AppException e) {
            model.addAttribute("message", e.getMessage());
            return "page/login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/login";
    }
}
