package com.example.askar.KanatTole.Models;

/**
 * Created by askar on 28.03.2018.
 */

public class Item {
    private int image;
    private String name;
    private int cost;
    private int amount;

    public Item(int image, String name, int cost, int amount) {
        this.image = image;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "image=" + image +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", amount=" + amount +
                '}';
    }
}
