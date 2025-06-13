package com.example.project1.model;

public class FirstAidTopic {
    private final int id;
    private final String title;
    private final int imageResId;

    public FirstAidTopic(int id, String title, int imageResId) {
        this.id = id;
        this.title = title;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
}