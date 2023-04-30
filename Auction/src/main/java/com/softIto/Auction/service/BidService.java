package com.softIto.Auction.service;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Bid;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.BidRepository;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.request.CreateBidRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService {

    private BidRepository bidRepository;
    private AuctionRepository auctionRepository;
    private AuctionService auctionService;
    private UserRepository userRepository;
    private UserService userService;
    private ItemRepository itemRepository;

    @Autowired
    public BidService(BidRepository bidRepository, AuctionRepository auctionRepository, UserService userService, AuctionService auctionService,
                      UserRepository userRepository, ItemRepository itemRepository) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.userService = userService;
        this.auctionService = auctionService;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public Bid createBid(Long auctionId, CreateBidRequest request) {

        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("User not found.");
        }

        Auction auction = auctionService.getById(auctionId);
        if (auction == null) {
            throw new EntityNotFoundException("Auction not found.");
        }

        if (!auction.isStatus()) {
            throw new IllegalStateException("Auction is not active.");
        }

        double currentBid = auction.getCurrentBid();

        if (request.getBid() <= currentBid) {
            throw new IllegalArgumentException("New bid should be greater than current bid.");
        }

        if (user.getBalance() < request.getBid()) {
            throw new IllegalStateException("User does not have enough balance.");
        }

        Bid bid = new Bid();
        bid.setUser(user);
        bid.setItem(auction.getItem());
        bid.setNewBid(request.getBid());

        auction.setCurrentBid(request.getBid());
        auction.setCurrentPrice(request.getBid());
        auction.setUser(user);
        user.setEmail(request.getEmail());

        auctionRepository.save(auction);
        bidRepository.save(bid);
        userRepository.save(user);
        itemRepository.save(auction.getItem());
        return bid;

    }


}
