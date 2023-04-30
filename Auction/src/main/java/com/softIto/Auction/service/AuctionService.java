package com.softIto.Auction.service;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.request.CreateAuctionRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuctionService {

    private UserRepository userRepository;
    private AuctionRepository auctionRepository;
    private ItemRepository itemRepository;


    @Autowired
    public AuctionService(AuctionRepository auctionRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;

    }


    public Auction createAuction(Long userId, CreateAuctionRequest request) {

        // Get the User from the database using the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Item
        Item item = new Item();
        item.setName(request.getItemName());
        item.setDescription(request.getItemDescription());
        item.setPrice(request.getItemPrice());
        item.setUser(user);

        // Create a new Auction
        Auction auction = new Auction();
        auction.setCreatorName(user.getFirstName() + " " + user.getLastName());
        auction.setCurrentPrice(item.getPrice());
        auction.setUser(user);
        auction.setItem(item);
        auction.setStatus(true);
        auction.setBids(new ArrayList<>());

        itemRepository.save(item);
        return auctionRepository.save(auction);

    }

    public Auction getById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + id));
    }

    public List<Auction> getAll(){
        return auctionRepository.findAll();
    }

}
