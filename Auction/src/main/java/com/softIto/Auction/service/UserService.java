package com.softIto.Auction.service;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.util.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuctionRepository auctionRepository;
    private ItemRepository itemRepository;


    @Autowired
    public UserService(UserRepository userRepository, AuctionRepository auctionRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.itemRepository = itemRepository;
    }

    public User saveUser(User user) {

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

    public void update(User entity) {
        userRepository.save(entity);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
