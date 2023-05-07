package com.softIto.Auction.controller;


import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Bid;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.request.CreateBidRequest;
import com.softIto.Auction.service.AuctionService;
import com.softIto.Auction.service.BidService;
import com.softIto.Auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bid")
public class BidController {

    private BidService bidService;
    private AuctionService auctionService;
    private ItemService itemService;

    @Autowired
    public BidController(BidService bidService,AuctionService auctionService,ItemService itemService) {
        this.bidService = bidService;
        this.auctionService=auctionService;
        this.itemService=itemService;

    }

    /*
    @PostMapping("/create/{auctionId}/{itemId}")
    public Bid createBid(@PathVariable Long auctionId, @PathVariable Long itemId, @RequestBody CreateBidRequest request) {
        return bidService.createBid(auctionId, itemId, request);
    }
     */

    @GetMapping("/bidForm")
    public String showBidPage(Model model) {
        Auction auction=new Auction();
        Item item=new Item();
        model.addAttribute("bid", new CreateBidRequest());
        model.addAttribute("auctionId",auction.getId());
        model.addAttribute("itemId",item.getId());
        return "bid";

    }


    @PostMapping("/create/{auctionId}/{itemId}")
    public String submitBid(@PathVariable Long auctionId, @PathVariable Long itemId,
                            @ModelAttribute("createBidRequest") CreateBidRequest createBidRequest, Model model) {


        try {
            bidService.createBid(auctionId, itemId, createBidRequest);
            return "redirect:/bid/home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "bid";
        }
    }

    @GetMapping("/home")
    public String userHome() {
        return "home";
    }


    @GetMapping("/get/{id}")
    public Bid getBid(@PathVariable Long id) {
        return bidService.getById(id);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteBid(@PathVariable Long id) {
        bidService.deleteById(id);
        return "Bid deleted";
    }

    @GetMapping("/getAll")
    public List<Bid> getAll() {
        return bidService.getAll();
    }
}
