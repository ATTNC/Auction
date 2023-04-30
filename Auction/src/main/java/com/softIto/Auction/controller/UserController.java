package com.softIto.Auction.controller;

import com.softIto.Auction.model.User;
import com.softIto.Auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user,
                        Model model) {
        User correctUser = userService.getByEmail(user.getEmail());
        if (Objects.equals(user.getPassword(), correctUser.getPassword())) {
            return "redirect:/homepage";
        } else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "/login";
        }
    }

}
