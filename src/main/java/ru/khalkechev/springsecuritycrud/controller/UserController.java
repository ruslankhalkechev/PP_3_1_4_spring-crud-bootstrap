package ru.khalkechev.springsecuritycrud.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.khalkechev.springsecuritycrud.model.User;
import ru.khalkechev.springsecuritycrud.service.CustomUserDetailsService;
import ru.khalkechev.springsecuritycrud.service.UserService;
import ru.khalkechev.springsecuritycrud.util.UserSessionInfo;
import ru.khalkechev.springsecuritycrud.util.UserValidator;

import java.security.Principal;


@Controller
@RequestMapping("/")
@SessionAttributes("userSessionInfo")
public class UserController {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, CustomUserDetailsService customUserDetailsService,
                          PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @ModelAttribute(name = "userSessionInfo")
    public UserSessionInfo setSessionInfo(Authentication authentication) {
        UserSessionInfo userInfo = new UserSessionInfo(authentication);
        return userInfo;
    }

    @GetMapping("/admin")
    public String showList(@ModelAttribute("user") User user,
                           @ModelAttribute("updatedUser") User updatedUser,
                           Model model) {
        model.addAttribute("userList", userService.getListOfUsers());
        return "/admin/index";
    }


    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, Errors errors) {
        userValidator.validate(user, errors);
        if (errors.hasErrors()) {
            return "redirect:/admin";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("updatedUser") @Valid User updatedUser, Errors errors,
                         @PathVariable("id") long id, Model model) {

        User userForUpdate = userService.getUserById(id);
        if (!userForUpdate.getName().equals(updatedUser.getName())) {
            userValidator.validate(updatedUser, errors);
        }

        if (errors.hasErrors()) {
            return "redirect:/admin";
        }
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userService.updateById(updatedUser, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUserInfo(Model model, Principal principal) {
        User user = customUserDetailsService.findByUserName(principal.getName());
        model.addAttribute("user", user);
        return "user/showuser";
    }

}
