package com.softIto.Auction.dto;

public class ItemDto {
    private String name;
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ItemDto(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }
}
