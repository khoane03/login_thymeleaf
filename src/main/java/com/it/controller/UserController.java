package com.it.controller;

import com.it.dto.request.UserUpdateDto;
import com.it.dto.response.UserDto;
import com.it.exception.AppException;
import com.it.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/list_user")
    public String listUser(HttpSession session,
                           Model model,
                           @RequestParam(defaultValue = "1") Integer pageIndex,
                           @RequestParam(defaultValue = "") String keyword) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        Page<UserDto> userDtos = userService.getAll(pageIndex, keyword);
        model.addAttribute("totalPages", userDtos.getTotalPages());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("users", userDtos);

        return "page/user";
    }

    @GetMapping("/update_user")
    public String updateUser(HttpSession session, Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("update", new UserUpdateDto());
        return "page/update_info";
    }

    @PostMapping("/update_user")
    public String updateUser(@RequestParam String username,
                             @ModelAttribute UserUpdateDto userUpdateDto,
                             RedirectAttributes redirectAttributes) {
        try {

            userService.updateUser(username, userUpdateDto);
        } catch (AppException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/update_user?username=" + username;
        }
        return "redirect:/";
    }

    @GetMapping("/disable_user")
    public String disableUser(@RequestParam String username) {
        userService.disableUser(username);
        return "redirect:/list_user";
    }

    @GetMapping("/enable_user")
    public String enableUser(@RequestParam String username) {
        userService.enableUser(username);
        return "redirect:/list_user";
    }
}
