package com.softIto.Auction.controller;

import com.softIto.Auction.model.Payment;
import com.softIto.Auction.model.User;
import com.softIto.Auction.request.PaymentRequest;
import com.softIto.Auction.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);

    }

    @DeleteMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "Payment deleted";
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Payment> getUser(@PathVariable Long id) {
        return new ResponseEntity<Payment>(paymentService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

}
