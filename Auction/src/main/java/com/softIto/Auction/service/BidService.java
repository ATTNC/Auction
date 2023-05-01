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
import com.softIto.Auction.response.AuctionSoldResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BidService {

    private BidRepository bidRepository;
    private AuctionRepository auctionRepository;
    private AuctionService auctionService;
    private UserRepository userRepository;
    private UserService userService;
    private ItemRepository itemRepository;
    private ItemService itemService;

    @Autowired
    public BidService(BidRepository bidRepository, AuctionRepository auctionRepository, UserService userService, AuctionService auctionService,
                      UserRepository userRepository, ItemRepository itemRepository, ItemService itemService) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
        this.userService = userService;
        this.auctionService = auctionService;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    public Bid createBid(Long auctionId, Long itemId, CreateBidRequest request) {

        AuctionSoldResponse response = new AuctionSoldResponse();

        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("User not found.");
        }

        Auction auction = auctionService.getById(auctionId);
        if (auction == null) {
            throw new EntityNotFoundException("Auction not found.");
        }


        if (Objects.equals(auction.getUser().getEmail(), request.getEmail())) {
            throw new IllegalStateException("You can't bid on an auction you created");
        }


        if (!auction.isStatus()) {
            throw new IllegalStateException("Auction is not active.");
        }

        Item item = itemService.getById(itemId);

        if (item == null) {
            throw new EntityNotFoundException("Item not found.");
        }

        double currentBid = auction.getCurrentBid();

        if (request.getBid() <= currentBid) {
            throw new IllegalArgumentException("New bid should be greater than current bid.");
        }

        if (user.getBalance() < request.getBid()) {
            throw new IllegalStateException("User does not have enough balance.");
        }

        if (auction.getHighestBidder() != null && auction.getHighestBidder().equals(request.getEmail())) {
            throw new IllegalStateException("You can't bid on this auction until someone else places a higher bid.");

        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDate, formatter);


        request.setBidDate(localDateTime);

        if (request.getBidDate().isAfter(auction.getEndDate())) {
            auction.setStatus(false);
            throw new DateTimeException("Auction time expired");

        }

        Bid bid = new Bid();
        bid.setUser(user);
        bid.setItem(item);
        bid.setAuction(auction);
        bid.setBid(request.getBid());
        bid.setBidTime(localDateTime);


        auction.setCurrentBid(request.getBid());
        auction.setHighestBidder(user.getEmail());
        user.setEmail(request.getEmail());

        if (request.getBid() == auction.getInstantlyBuy()) {
            auction.setStatus(false);
            auction.setItemByPurchased(auction.getHighestBidder());
            response.setMessage("Congratulations! You can buy the item");

        }

        auctionRepository.save(auction);
        bidRepository.save(bid);
        userRepository.save(user);

        return bid;

    }

    public Bid getById(Long id) {
        return bidRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bid not found with id: " + id));
    }


}
