package com.softIto.Auction.controller;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.request.CreateAuctionRequest;
import com.softIto.Auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {


    private AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/create/{userId}")
    public Auction createAuction(@PathVariable Long userId, @RequestBody CreateAuctionRequest request) {
        return auctionService.createAuction(userId, request);
    }

    @GetMapping("/get/{id}")
    public Auction getAuction(@PathVariable Long id) {
        return auctionService.getById(id);
    }
    @GetMapping("/getAll")
    public List<Auction> getAll(){
        return auctionService.getAll();
    }
}
