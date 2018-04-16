package com.example.askar.KanatTole.Fragments.HomeFragment;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class TimeTole {
    private ArrayList<String> products=new ArrayList<>();
    private  ArrayList<Entry> entries=new ArrayList<>();
    private ArrayList<String> labels=new ArrayList<>();

    public TimeTole(ArrayList<String> products, ArrayList<Entry> entries, ArrayList<String> labels) {
        this.products = products;
        this.entries = entries;
        this.labels = labels;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }
}
