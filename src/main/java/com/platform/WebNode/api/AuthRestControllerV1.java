package com.platform.WebNode.api;

import com.platform.WebNode.entity.User;
import com.platform.WebNode.repository.UserRepository;
import com.platform.WebNode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/auth/rest/api/v1")
public class AuthRestControllerV1 {

    @Autowired
    private UserRepository userRepository;
    private final UserService userService;
    @Autowired
    public AuthRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-test/{username}")
    public User getUserByName(@PathVariable("username") String username) {

        return userRepository.getUserByUsername(username);
    }

    @GetMapping("/user-delete/{id}")
    public Set<User> delete(@PathVariable("id") long id) {

        userService.deleteUserById(id);
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") int id) {

        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public Set<User> users() {

        return userService.getUsers();
    }

    @GetMapping("/getCurrentUserId")
    public String getCurrentUserId(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticationUser = "";
        if (principal instanceof UserDetails) {
            authenticationUser = ((UserDetails) principal).getUsername();
        } else {
            authenticationUser = principal.toString();
        }

        User userId = null;
        try {
            String finalAuthenticationUser = authenticationUser;
            userId = StreamSupport.stream(userService.getUsers().spliterator(), false)
                    .filter(user -> (user.getUsername().toString().equals(finalAuthenticationUser.toString())))
                    .findAny()
                    .orElse(null);

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return userId.getId().toString();
    }
}