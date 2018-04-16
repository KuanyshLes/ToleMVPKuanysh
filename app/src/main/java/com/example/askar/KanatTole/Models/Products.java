package com.example.askar.KanatTole.Models;

/**
 * Created by asus on 21.03.2018.
 */

public class Products {


    String title;
    String price;
    String count;
    int image;


    public Products(String title, String price, String count, int image) {
        this.title = title;
        this.price = price;
        this.count = count;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Products(String title, String price, String count) {

        this.title = title;
        this.price = price;
        this.count = count;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



}
