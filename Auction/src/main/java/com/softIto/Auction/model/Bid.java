package com.softIto.Auction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    private double currentBid;
    private double newBid;

}
