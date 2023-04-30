package com.softIto.Auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double currentBid;
    private double startPrice;
    private double instantlyBuy;
    private String creatorName;
    private String highestBidder;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("auction")
    private User user;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties("auction")
    private Item item;
    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("auction")
    private List<Bid> bids;
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getInstantlyBuy() {
        return instantlyBuy;
    }

    public void setInstantlyBuy(double instantlyBuy) {
        this.instantlyBuy = instantlyBuy;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @JsonIgnore
    public User getUser() {
        return Hibernate.unproxy(user, User.class);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Item getItem() {
        return Hibernate.unproxy(item, Item.class);
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
