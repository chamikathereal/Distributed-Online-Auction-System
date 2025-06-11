package io.github.chamikathereal.auction.ejb.remote;

import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.exception.InvalidBidException;
import jakarta.ejb.Remote;

@Remote
public interface BidManager {
    void placeBid(Bid bid) throws InvalidBidException;
}
