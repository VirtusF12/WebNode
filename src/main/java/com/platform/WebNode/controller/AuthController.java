package com.platform.WebNode.controller;

import com.platform.WebNode.entity.User;
import com.platform.WebNode.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);
    @Value("${project.name:ProjectName}")
    private String projectName;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    UserRepository userRepository;

    private String authUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    private User getAuthUser() {
        return userRepository.
                findAll().
                stream().
                filter(u -> u.getUsername().equals(authUserName())).findFirst().get();
    }

    @GetMapping()
    public String auth(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.isAuthenticated()) {
            String username = auth.getName();
            model.addAttribute("title", String.format("Контакты - %s", projectName));
            model.addAttribute("username", authUserName());
            return "contact";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/user/profile/{username}")
    public String getProfile(@PathVariable(name="username") String username,
                             Model model) {

        try {
            User user = getAuthUser();
            model.addAttribute("title", String.format("Профиль - %s", projectName));
            model.addAttribute("user", user);
            return "profile";
        } catch (Exception ex) {
            return "redirect:/auth";
        }
    }

    @GetMapping("/user/edit")
    public String getEditContact(Model model) {

        User user = getAuthUser();
        model.addAttribute("title", String.format("Редактирование пользователя - %s", projectName));
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/user/edit")
    public String postEditContact(@ModelAttribute User user,
                                  @RequestParam("file") MultipartFile file,
                                  Model model) throws IOException {

        User authUser = getAuthUser();

        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                log.error("file != null " + file.getName());
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                log.error("файл был загружен...");
                model.addAttribute("resultFile", resultFileName);
                log.error("file = " + resultFileName);
                /* path to file (MvcConfig, application properties) */
                authUser.setPathImg("/img/"+resultFileName);
            } else {
                log.error("file == null");
            }
            String username = authUser.getUsername();
            authUser.setUsername(username);
            authUser.setPassword(authUser.getPassword());
            authUser.setFullName(user.getFullName());
            String phone = authUser.getPhone() == null ? "" : authUser.getPhone();
            if (userRepository.findAll().stream().filter(u -> (u.getPhone() == null ? "": u.getPhone()).
                                                              equals(user.getPhone())).count() == 0) {
                authUser.setPhone(user.getPhone());
            } else {
                authUser.setPhone(phone);
            }
            authUser.setDescription(user.getDescription());
            authUser.setDob(user.getDob());
            String email = authUser.getEmail() == null ? "" : authUser.getEmail();
            if (userRepository.findAll().stream().filter(u -> (u.getEmail() == null ? "" : u.getEmail()).
                                                              equals(user.getEmail())).count() == 0) {
                authUser.setEmail(user.getEmail());
            } else {
                authUser.setEmail(email);
            }
            userRepository.save(authUser);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return "redirect:/auth/user/edit";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         Model model) {

        userRepository.deleteById(id);
        return "redirect:/auth/show";
    }
}