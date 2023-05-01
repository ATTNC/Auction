package com.softIto.Auction.controller;

import com.softIto.Auction.model.Payment;
import com.softIto.Auction.request.PaymentRequest;
import com.softIto.Auction.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add/{userId}/{auctionId}/{bidId}")
    public Payment addPayment(@PathVariable Long userId, @PathVariable Long auctionId, @PathVariable Long bidId, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.addPayment(userId, auctionId, bidId, paymentRequest);
    }

}
