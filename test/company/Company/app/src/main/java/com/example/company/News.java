package com.example.company;

public class News {
    private String name;
    private String content;
    private int imageResourceId;

    public News(String name, String content, int imageResourceId) {
        this.name = name;
        this.content = content;
        this.imageResourceId = imageResourceId;
    }

    public static final News[] news = {
            new News("news one","aaaaaaaaaaaaaaaaaa",R.drawable.news01),
            new News("news two","bbbbbbbbbbbbbbbbbb",R.drawable.news02)
    };
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}


