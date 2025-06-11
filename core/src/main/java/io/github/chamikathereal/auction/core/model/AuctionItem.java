package io.github.chamikathereal.auction.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuctionItem implements Serializable {
    private int id;
    private String name;
    private String description;
    private double startingPrice;
    private LocalDateTime expiresAt;

    public AuctionItem() {
    }

    public AuctionItem(int id, String name, String description, double startingPrice, LocalDateTime expiresAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.expiresAt = expiresAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

}
