package io.github.chamikathereal.auction.ejb.remote;

import io.github.chamikathereal.auction.core.model.Bid;
import jakarta.ejb.Remote;

@Remote
public interface Broadcaster {
    void broadcastBid(Bid bid);
}
