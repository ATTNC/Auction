package com.softIto.Auction.controller;

import com.softIto.Auction.model.Bid;
import com.softIto.Auction.request.CreateBidRequest;
import com.softIto.Auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bid")
public class BidController {

    private BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/create/{auctionId}/{itemId}")
    public Bid createBid(@PathVariable Long auctionId,@PathVariable Long itemId , @RequestBody CreateBidRequest request) {
        return bidService.createBid(auctionId,itemId, request);
    }

    @GetMapping("/get/{id}")
    public Bid getBid(@PathVariable Long id) {
        return bidService.getById(id);

    }
}
