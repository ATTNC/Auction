package com.softIto.Auction.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("items")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    @JsonIgnoreProperties("items")
    private Auction auction;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnoreProperties("items")
    private Category category;



}
