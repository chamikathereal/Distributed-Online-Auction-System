package io.github.chamikathereal.auction.core.model;

import java.io.Serializable;

public class Bid implements Serializable {
    private String bidderName;
    private double amount;
    private int itemId;
    private String email;

    public Bid() {
    }

    public Bid(String bidderName, double amount, int itemId, String email) {
        this.bidderName = bidderName;
        this.amount = amount;
        this.itemId = itemId;
        this.email = email;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
