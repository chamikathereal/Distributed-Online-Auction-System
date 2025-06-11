package io.github.chamikathereal.auction.ejb.bean;

import io.github.chamikathereal.auction.core.model.AuctionItem;
import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.exception.InvalidBidException;
import io.github.chamikathereal.auction.ejb.remote.AuctionManager;
import io.github.chamikathereal.auction.ejb.remote.BidManager;
import io.github.chamikathereal.auction.ejb.remote.Broadcaster;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Stateless
@Remote(BidManager.class)
public class BidManagerBean implements BidManager {

    @EJB
    private AuctionManager auctionManager;

    @EJB
    private Broadcaster broadcaster;

    @Override
    public void placeBid(Bid bid) throws InvalidBidException {
        AuctionItem item = auctionManager.getItemById(bid.getItemId());
        if (item == null) {
            System.out.printf("[BID][FAIL] Item #%d does not exist.%n", bid.getItemId());
            throw new InvalidBidException("Item does not exist");
        }

        double currentHighest = auctionManager.getHighestBidForItem(bid.getItemId())
                .map(Bid::getAmount)
                .orElse(item.getStartingPrice());

        if (bid.getAmount() <= currentHighest) {
            System.out.printf("[BID][FAIL] Bid $%.2f not higher than current $%.2f%n", bid.getAmount(), currentHighest);
            throw new InvalidBidException("Bid must be higher than current highest: $" + currentHighest);
        }

        auctionManager.saveBid(bid);
        System.out.printf("[BID][SAVE] Bid saved: %s ($%.2f) on item #%d%n", bid.getBidderName(), bid.getAmount(), bid.getItemId());

        broadcaster.broadcastBid(bid);
        System.out.printf("[BID][BROADCAST] Bid broadcasted for item #%d by %s%n", bid.getItemId(), bid.getBidderName());
    }
}

