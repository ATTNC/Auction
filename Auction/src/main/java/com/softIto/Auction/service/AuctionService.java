package com.softIto.Auction.service;


import com.softIto.Auction.model.*;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.CategoryRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.request.CreateAuctionRequest;
import com.softIto.Auction.response.AuctionSoldResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class AuctionService {

    // End time hatası ekle!

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
        AuctionSoldResponse response = new AuctionSoldResponse();
        // Get the User from the database using the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setName(request.getCategoryName());
        categoryRepository.save(category);



        Item item = new Item();
        item.setName(request.getItemName());
        item.setDescription(request.getItemDescription());
        item.setPrice(request.getItemPrice());
        item.setUser(user);
        item.setCategory(category);

        Auction auction = new Auction();
        auction.setCreatorName(user.getFirstName() + " " + user.getLastName());
        auction.setStartPrice(request.getItemPrice());
        auction.setInstantlyBuy(request.getInstantlyBuy());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDate, formatter);

        if (request.getStartDate().isBefore(localDateTime)) {
            throw new DateTimeException("Please select a valid time");
        }
        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new DateTimeException("Please select a valid time");
        }
        if(!request.getEndDate().isAfter(request.getStartDate().plusMinutes(59))){
            throw new DateTimeException("The auction interval must be at least 1 hour");
        }



        if (localDateTime == request.getEndDate() && auction.getHighestBidder() != null) {
            auction.setItemByPurchased(auction.getHighestBidder());
            auction.setStatus(false);
            response.setMessage("The auction period is over and the winner is " + auction.getItemByPurchased());

        }

        auction.setStartDate(request.getStartDate());
        auction.setEndDate(request.getEndDate());


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


    public void getItemImage(Long id){
        itemRepository.findById(id);
    }


    public Auction getById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + id));
    }

    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    public Auction updateAuction(Long id, Long userId, Auction updatedAuction) {

        Auction auction = getById(id);

        // New
        if (!Objects.equals(auction.getUser().getId(), userId)) {
            throw new IllegalArgumentException("Auction and user do not match");
        }

        auction.setStartDate(updatedAuction.getStartDate());
        auction.setEndDate(updatedAuction.getEndDate());
        auction.setStartPrice(updatedAuction.getStartPrice());
        auction.setInstantlyBuy(updatedAuction.getInstantlyBuy());


        return auctionRepository.save(auction);
    }

}
