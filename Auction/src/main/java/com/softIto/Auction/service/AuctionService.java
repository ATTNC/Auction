package com.softIto.Auction.service;

import com.softIto.Auction.model.*;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.CategoryRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.request.CreateAuctionRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AuctionService {

    private UserRepository userRepository;
    private AuctionRepository auctionRepository;
    private ItemRepository itemRepository;
    private ItemService itemService;
    private UserService userService;
    private CategoryRepository categoryRepository;


    @Autowired
    public AuctionService(AuctionRepository auctionRepository, UserRepository userRepository, ItemRepository itemRepository,
                          ItemService itemService, UserService userService, CategoryRepository categoryRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;

    }


    public Auction createAuction(Long userId, CreateAuctionRequest request) {

        // Get the User from the database using the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setName(request.getCategoryName());
        categoryRepository.save(category);


        // Create a new Item
        Item item = new Item();
        item.setName(request.getItemName());
        item.setDescription(request.getItemDescription());
        item.setPrice(request.getItemPrice());
        item.setUser(user);
        item.setCategory(category);
        // Create a new Auction
        Auction auction = new Auction();
        auction.setCreatorName(user.getFirstName() + " " + user.getLastName());
        auction.setStartPrice(request.getItemPrice());
        auction.setInstantlyBuy(request.getInstantlyBuy());
        auction.setUser(user);
        auction.setItem(item);
        auction.setStatus(true);


        item.setAuction(auction);
        itemRepository.save(item);
        return auctionRepository.save(auction);

    }

    public void uploadFile(Long itemId, MultipartFile file) throws IOException {

        Item item = itemService.getById(itemId);
        if (item == null) {
            throw new EntityNotFoundException("Item not found.");
        }
        item.setImage(file.getBytes());
        itemRepository.save(item);

    }

    public Auction getById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + id));
    }

    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }


}
