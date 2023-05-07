package com.softIto.Auction.controller;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.request.CreateAuctionRequest;
import com.softIto.Auction.service.AuctionService;
import com.softIto.Auction.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    private AuctionService auctionService;
    private ItemService itemService;


    @Autowired
    public AuctionController(AuctionService auctionService, ItemService itemService) {
        this.auctionService = auctionService;
        this.itemService = itemService;
    }


/*
    @PostMapping("/create/{userId}")
    public Auction createAuction(@PathVariable Long userId, @RequestBody CreateAuctionRequest request) {
        return auctionService.createAuction(userId, request);
    }

 */


    @GetMapping("/create/{userId}")
    public String showAuctionForm(@PathVariable Long userId, Model model) {
        model.addAttribute("auction", new CreateAuctionRequest());
        return "create-auction";
    }


    @PostMapping("/create/{userId}")
    public String createAuction(@PathVariable Long userId, @ModelAttribute("createAuctionRequest") CreateAuctionRequest createAuctionRequest, Model model) {
        try {
            auctionService.createAuction(userId, createAuctionRequest);
            return "redirect:/auction/home";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "create-auction";
        }
    }

    @GetMapping("/home")
    public String userHome() {
        return "home";
    }


    @GetMapping("/auctions")
    public String auctions(Model model) {
        List<Auction> auctions = auctionService.getAll();
        model.addAttribute("auctions", auctions);
        return "auctions";
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

    /*
    @GetMapping("/image/{id}")
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {

        Item image=itemService.getById(id);

        if (image != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(image.getData());
        }
    }

     */


}
