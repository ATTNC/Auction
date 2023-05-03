package com.softIto.Auction.controller;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.request.CreateAuctionRequest;
import com.softIto.Auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PutMapping("/update/{id}/{userId}")
    public Auction updateAuction(@PathVariable Long id, @PathVariable Long userId, @RequestBody Auction auction) {
        return auctionService.updateAuction(id, userId, auction);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return "Auction deleted";
    }

    @GetMapping("/get/{id}")
    public Auction getAuction(@PathVariable Long id) {
        return auctionService.getById(id);
    }

    @GetMapping("/getAll")
    public List<Auction> getAll() {
        return auctionService.getAll();
    }

    @PostMapping("/uploadImage/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        auctionService.uploadFile(id, file);
        return "Image saved on database";
    }


}
