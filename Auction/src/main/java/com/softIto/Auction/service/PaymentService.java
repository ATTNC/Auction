package com.softIto.Auction.service;

import com.softIto.Auction.model.Auction;
import com.softIto.Auction.model.Bid;
import com.softIto.Auction.model.Payment;
import com.softIto.Auction.model.User;
import com.softIto.Auction.repository.AuctionRepository;
import com.softIto.Auction.repository.BidRepository;
import com.softIto.Auction.repository.PaymentRepository;
import com.softIto.Auction.repository.UserRepository;
import com.softIto.Auction.request.PaymentRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private UserService userService;
    private UserRepository userRepository;
    private AuctionService auctionService;
    private BidService bidService;
    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, UserService userService, UserRepository userRepository,
                          AuctionService auctionService, BidService bidService, AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.auctionService = auctionService;
        this.bidService = bidService;
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public Payment addPayment(Long userId, Long auctionId, Long bidId, PaymentRequest paymentRequest) {
        User user = userService.getById(userId);

        if (user == null) {
            throw new EntityNotFoundException("User not found.");
        }

        Auction auction = auctionService.getById(auctionId);
        if (auction == null) {
            throw new EntityNotFoundException("Auction not found.");
        }

        Bid bid = bidService.getById(bidId);
        if (bid == null) {
            throw new EntityNotFoundException("Bid not found.");
        }
        if(!Objects.equals(auction.getHighestBidder(), user.getEmail())){

            throw new IllegalStateException("You are not the winner of the auction");
        }

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAuction(auction);
        payment.setBid(bid);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(paymentRequest.getAmount());

        if(paymentRequest.getAmount()<auction.getCurrentBid()){
            throw new IllegalStateException("Amount is cannot be less than purchase price");
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDate, formatter);

        paymentRequest.setPaymentDate(localDateTime);
        payment.setPaymentDate(localDateTime);

        user.setBalance(user.getBalance() - payment.getAmount());
        payment.setStatus(true);


        auctionRepository.save(auction);
        bidRepository.save(bid);
        userRepository.save(user);


        return paymentRepository.save(payment);
    }
}
