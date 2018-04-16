package com.example.askar.KanatTole.API.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Items implements Parcelable {

    @SerializedName("id")
    @Expose
    private String itemId;

    @SerializedName("title")
    @Expose
    private String itemTitle;

    @SerializedName("manufacturer")
    @Expose
    private String itemManufacturer;

    @SerializedName("barCode")
    @Expose
    private String itemBarCode;

    @SerializedName("retailCost")
    @Expose
    private String itemRetailCost;

    @SerializedName("purchaseCost")
    @Expose
    private String itemPurchaseCost;

    @SerializedName("amount")
    @Expose
    private String itemAmount;

    public Items(String itemId,
                 String itemTitle,
                 String itemManufacturer,
                 String itemBarCode,
                 String itemRetailCost,
                 String itemPurchaseCost,
                 String itemAmount) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemManufacturer = itemManufacturer;
        this.itemBarCode = itemBarCode;
        this.itemRetailCost = itemRetailCost;
        this.itemPurchaseCost = itemPurchaseCost;
        this.itemAmount = itemAmount;
    }

    protected Items(Parcel in) {
        itemId = in.readString();
        itemTitle = in.readString();
        itemAmount = in.readString();
        itemPurchaseCost = in.readString();
        itemRetailCost = in.readString();
        itemBarCode = in.readString();
        itemManufacturer = in.readString();

    }




    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemManufacturer() {
        return itemManufacturer;
    }

    public void setItemManufacturer(String itemManufacturer) {
        this.itemManufacturer = itemManufacturer;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getItemRetailCost() {
        return itemRetailCost;
    }

    public void setItemRetailCost(String itemRetailCost) {
        this.itemRetailCost = itemRetailCost;
    }

    public String getItemPurchaseCost() {
        return itemPurchaseCost;
    }

    public void setItemPurchaseCost(String itemPurchaseCost) {
        this.itemPurchaseCost = itemPurchaseCost;
    }


    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    @Override
    public String
    toString() {
        return "Items{" +
                "itemId='" + itemId + '\'' +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemManufacturer='" + itemManufacturer + '\'' +
                ", itemBarCode='" + itemBarCode + '\'' +
                ", itemRetailCost='" + itemRetailCost + '\'' +
                ", itemPurchaseCost='" + itemPurchaseCost + '\'' +
                ", itemAmount='" + itemAmount + '\'' +
                '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(itemTitle);
        dest.writeString(itemPurchaseCost);
        dest.writeString(itemRetailCost);
        dest.writeString(itemBarCode);

        dest.writeString(itemAmount);
        dest.writeString(itemManufacturer);
    }

}
