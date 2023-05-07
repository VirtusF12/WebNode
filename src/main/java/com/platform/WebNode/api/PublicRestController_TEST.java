package com.platform.WebNode.api;

import com.platform.WebNode.entity.User;
import com.platform.WebNode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/rest/api/v1")
public class PublicRestController_TEST {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{username}")
    public User getUserByName(@PathVariable("username") String username) {
        return userRepository.getUserByUsername(username);
    }
}
