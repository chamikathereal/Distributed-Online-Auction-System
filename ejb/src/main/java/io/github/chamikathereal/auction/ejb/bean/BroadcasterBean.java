package io.github.chamikathereal.auction.ejb.bean;

import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.remote.Broadcaster;
import jakarta.annotation.Resource;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.jms.*;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Stateless
@Remote(Broadcaster.class)
public class BroadcasterBean implements Broadcaster {

    @Resource(lookup = "jms/AuctionConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/AuctionTopic")
    private Topic auctionTopic;

    @Override
    public void broadcastBid(Bid bid) {
        try (JMSContext context = connectionFactory.createContext()) {
            JsonObject bidJson = Json.createObjectBuilder()
                    .add("itemId", bid.getItemId())
                    .add("bidderName", bid.getBidderName() == null ? "" : bid.getBidderName())
                    .add("email", bid.getEmail() == null ? "" : bid.getEmail())
                    .add("amount", bid.getAmount())
                    .build();

            TextMessage message = context.createTextMessage(bidJson.toString());

            System.out.printf("[JMS][SEND] Broadcasting bid: %s%n", bidJson);
            context.createProducer().send(auctionTopic, message);

        } catch (JMSRuntimeException e) {
            System.err.printf("[JMS][ERROR] Error broadcasting bid: %s%n", e.getMessage());
            throw new RuntimeException("Error broadcasting bid", e);
        }
    }

}
