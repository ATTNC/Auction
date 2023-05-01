package com.softIto.Auction.controller;

import com.softIto.Auction.dto.ItemDto;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/get/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping("/getAll")
    public List<ItemDto> getAll() {
        return itemService.getAll();
    }


}
