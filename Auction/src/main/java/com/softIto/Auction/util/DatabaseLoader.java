package com.softIto.Auction.util;

import com.softIto.Auction.enums.Role;
import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Category;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.CategoryRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, AuctionRepository auctionRepository, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setBalance(100000);
        user.setEmail("test@hotmail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        userRepository.save(user);




    }
}
