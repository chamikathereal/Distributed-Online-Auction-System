package io.github.chamikathereal.auction.ejb.bean;

import io.github.chamikathereal.auction.core.model.AuctionItem;
import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.remote.AuctionManager;
import jakarta.ejb.Local;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
@Local(AuctionManager.class)
public class AuctionManagerBean implements AuctionManager {

    private final List<AuctionItem> items = new ArrayList<>();
    private final List<Bid> bids = new ArrayList<>();
    private int nextItemId = 1;

    @Lock(LockType.WRITE)
    @Override
    public void addItem(AuctionItem item) {
        item.setId(nextItemId++);
        items.add(item);
        System.out.println("[AuctionManagerBean] Added item: " + item.getName() + " (ID: " + item.getId() + ")");
    }

    @Lock(LockType.READ)
    @Override
    public List<AuctionItem> getItems() {
        System.out.println("[AuctionManagerBean] Retrieving all auction items. Total: " + items.size());
        return new ArrayList<>(items);
    }

    @Lock(LockType.WRITE)
    @Override
    public void saveBid(Bid bid) {
        bids.add(bid);
        System.out.println("[AuctionManagerBean] Saved bid: $" + bid.getAmount() + " by " + bid.getBidderName() + " for item ID: " + bid.getItemId());
    }

    @Lock(LockType.READ)
    @Override
    public List<Bid> getAllBids() {
        List<Bid> sorted = new ArrayList<>(bids);
        sorted.sort((b1, b2) -> Double.compare(b2.getAmount(), b1.getAmount())); // Descending
        System.out.println("[AuctionManagerBean] Returning all bids, sorted by amount. Total: " + sorted.size());
        return sorted;
    }

    @Lock(LockType.READ)
    @Override
    public Optional<Bid> getHighestBidForItem(int itemId) {
        Optional<Bid> highest = bids.stream()
                .filter(bid -> bid.getItemId() == itemId)
                .max((b1, b2) -> Double.compare(b1.getAmount(), b2.getAmount()));
        System.out.println("[AuctionManagerBean] Highest bid for item ID " + itemId + ": " +
                (highest.isPresent() ? "$" + highest.get().getAmount() : "None"));
        return highest;
    }

    @Lock(LockType.READ)
    @Override
    public AuctionItem getItemById(int itemId) {
        AuctionItem found = items.stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
        System.out.println("[AuctionManagerBean] getItemById(" + itemId + "): " +
                (found != null ? found.getName() : "Not found"));
        return found;
    }
}
