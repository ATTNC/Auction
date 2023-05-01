package com.softIto.Auction.service;

import com.softIto.Auction.dto.ItemDto;
import com.softIto.Auction.model.Item;
import com.softIto.Auction.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /*
     public Item saveItem(MultipartFile itemImage, Item item) throws Exception {

         item.setItemImage(itemImage);

         String fileName = StringUtils.cleanPath(itemImage.getOriginalFilename());
         item.setImageUrl("/images/" + fileName);

         Item savedItem = itemRepository.save(item);

         String uploadDir = "src/main/resources/static/images/" + savedItem.getId();

         FileUploadUtil.saveFile(uploadDir, fileName, itemImage);

         return savedItem;

     }


     */

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auction not found with id: " + id));
    }

    public List<ItemDto> getAll() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items) {
            ItemDto itemDto = new ItemDto(item.getName(), item.getImage());
            itemDtos.add(itemDto);
        }

        return itemDtos;
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Long id, Item updatedItem) {

        Item item = getById(id);
        item.setPrice(updatedItem.getPrice());
        item.setName(updatedItem.getName());
        item.setDescription(updatedItem.getDescription());
        item.setImage(updatedItem.getImage());

        return itemRepository.save(item);
    }
}
