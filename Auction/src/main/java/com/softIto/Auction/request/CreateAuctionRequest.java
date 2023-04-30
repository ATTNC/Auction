package com.softIto.Auction.request;

import com.softIto.Auction.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionRequest {
    private String itemName;
    private double itemPrice;
    private double instantlyBuy;
    private String itemDescription;
    private String categoryName;
}
