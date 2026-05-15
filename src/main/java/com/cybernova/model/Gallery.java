package com.cybernova.model;

import java.sql.Timestamp;

public class Gallery {

    private int galleryId;
    private String title;
    private String caption;
    private String category;
    private String imageType;
    private byte[] imageData;
    private Timestamp createdDate;

    public Gallery() {}

    public int getGalleryId() { return galleryId; }
    public void setGalleryId(int galleryId) { this.galleryId = galleryId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }

    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }
}
