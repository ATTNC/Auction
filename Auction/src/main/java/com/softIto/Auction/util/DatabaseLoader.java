package com.softIto.Auction.util;

import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setBalance(100000);
        user.setEmail("test@hotmail.com");
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        userRepository.save(user);

    }
}
