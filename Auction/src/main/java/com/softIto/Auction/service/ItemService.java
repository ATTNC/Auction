package com.softIto.Auction.service;

import com.softIto.Auction.model.Item;
import com.softIto.Auction.repository.ItemRepository;
import com.softIto.Auction.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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


}
