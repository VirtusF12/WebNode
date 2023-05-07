package com.platform.WebNode.controller;

import com.platform.WebNode.entity.User;
import com.platform.WebNode.repository.UserRepository;
import com.platform.WebNode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class PublicController {

    Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title", "WebNode - Главная страница");
        return "index";
    }

    @GetMapping("reg")
    public String registration(@ModelAttribute("user") User user, Model model) {

        model.addAttribute("title","WebNode - Регистрация пользователя");
        return "reg";
    }

    @PostMapping("reg")
    public String registrationUser(@ModelAttribute("user") User user, Model model) {

        user.setPathImg("/static/img/contact/default_img_contact.jpg");
        model.addAttribute("title","WebNode - Регистрация пользователя");

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("isShowErrorMessageByPassword", true);
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "reg";
        }

        if (!userService.saveUser(user)) {
            model.addAttribute("isShowErrorMessageByUsername", true);
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "reg";
        }

        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("title", "Вход - WebNode");
        return "login";
    }

    @PostMapping("login")
    public String loginPage(Model model) {
        return "redirect:/auth";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        log.info(String.format("request = '%s'", request.toString()));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }

    @Deprecated
    @PostMapping()
    public String create_test(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("dob") Object dob,
                         Model model) {

        System.out.println(username + " : " + password + " : " + dob);

        return "redirect:/";
    }
}
