package com.automarks.multitech.smart_gadi.Pojo;

/**
 * Created by prakash on 8/21/2017.
 */

public class Testimonial_data {
    String title;
    String name;
    String reviewtext;
    String desctiption;

    String image;

    public Testimonial_data(String title,String name, String reviewtext, String image,String desctiption) {
        this.name = name;
        this.reviewtext = reviewtext;
        this.image = image;
        this.title=title;
        this.desctiption=desctiption;
    }

    public String getTitle() {
        return title;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
