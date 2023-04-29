package com.softIto.Auction.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionRequest {
    private String itemName;
    private double itemPrice;
    private String itemDescription;
    private String categoryName;

}
