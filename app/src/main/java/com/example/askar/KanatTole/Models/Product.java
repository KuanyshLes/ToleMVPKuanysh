package com.example.askar.KanatTole.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public String product_name;
    public String product_price;
    public String product_amount;
    public int product_image;


    public Product(String product_name, String product_price, String product_amount, int product_image) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_amount = product_amount;
        this.product_image = product_image;
    }


    protected Product(Parcel in) {
        product_name = in.readString();
        product_price = in.readString();
        product_amount = in.readString();
        product_image = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getProduct_amount() {
        return product_amount;
    }

    public int getProduct_image() {
        return product_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_amount);
        dest.writeString(product_name);
        dest.writeString(product_price);
        dest.writeInt(product_image);
    }
}
