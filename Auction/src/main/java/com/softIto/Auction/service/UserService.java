package com.softIto.Auction.service;

import com.softIto.Auction.controller.UserController;
import com.softIto.Auction.enums.Role;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuctionRepository auctionRepository;
    private ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserService(UserRepository userRepository, AuctionRepository auctionRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
    }

    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        LocalDateTime currentRequest = LocalDateTime.now();

        logger.info("User added {} ", user.getEmail() + " " + currentRequest);
        return userRepository.save(user);
    }


    public String getUserPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getPassword();
        } else {
            return null;
        }
    }

    public boolean isEmailValid(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already using!");

        }
        return true;

    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(Long id, User updatedUser) {
        User user = getById(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setBalance(updatedUser.getBalance());
        user.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
