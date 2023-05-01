package com.softIto.Auction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bids")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("bids")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "auction_id")
    @JsonIgnoreProperties("bids")
    private Auction auction;
    private double bid;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",shape = JsonFormat.Shape.STRING)
    LocalDateTime bidTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @JsonIgnore
    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",shape = JsonFormat.Shape.STRING)
    public LocalDateTime getBidTime() {
        return bidTime;
    }
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm",shape = JsonFormat.Shape.STRING)
    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}
