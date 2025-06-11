package io.github.chamikathereal.auction.ejb.remote;

import io.github.chamikathereal.auction.core.model.AuctionItem;
import io.github.chamikathereal.auction.core.model.Bid;
import java.util.List;
import java.util.Optional;

public interface AuctionManager {
    void addItem(AuctionItem item);
    List<AuctionItem> getItems();
    void saveBid(Bid bid);
    List<Bid> getAllBids();
    Optional<Bid> getHighestBidForItem(int itemId);
    AuctionItem getItemById(int itemId);
}
