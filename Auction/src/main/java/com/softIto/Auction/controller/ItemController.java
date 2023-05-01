package com.softIto.Auction.controller;

import com.softIto.Auction.dto.ItemDto;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public String addItem(@RequestBody Item item) {
        itemService.addItem(item);
        return "Item added";
    }

    @PutMapping("/update/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "Item deleted";
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
