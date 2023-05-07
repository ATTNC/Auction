package com.softIto.Auction.controller;

import com.softIto.Auction.model.User;
import com.softIto.Auction.request.LoginRequest;
import com.softIto.Auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

    // DI: Yoğun kullanılan classlar da kullanırız DI sayesinde Client tepki süresi düşürürüz.Özellikle JPA ,Log classlarında!
    // swashbuckle  swagger aynı işlevi görüyor.


    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "User deleted";
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistrationForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        userService.saveUser(user);
        return "redirect:/user/login";
    }


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String submitLoginPage(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model) {
        User corretUser = userService.getByEmail(loginRequest.getEmail());
        if (new BCryptPasswordEncoder().matches(loginRequest.getPassword(), corretUser.getPassword())) {
            return "redirect:/user/home";
        } else {
            return "login";
        }
    }

    @GetMapping("/home")
    public String userHome() {
        return "home";
    }



/*
    @PostMapping("/login")
    public String login(@RequestBody User user,
                        Model model, RedirectAttributes redirectAttributes) {
        User correctUser = userService.getByEmail(user.getEmail());
        if (new BCryptPasswordEncoder().matches(user.getPassword(), correctUser.getPassword())) {
            redirectAttributes.addFlashAttribute("message", "Login successful");
            return "redirect:/homepage";
        } else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "login";
        }
    }
*/

}
