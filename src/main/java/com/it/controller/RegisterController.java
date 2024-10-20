package com.it.controller;

import com.it.dto.UserDto;
import com.it.exception.AppException;
import com.it.service.UserService;
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
public class RegisterController {

    UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "page/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.save(userDto);
        } catch (AppException e) {
            model.addAttribute("message", e.getMessage());
            return "page/register";
        }
        return "redirect:/login";
    }
}