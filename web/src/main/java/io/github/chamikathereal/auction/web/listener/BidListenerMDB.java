package io.github.chamikathereal.auction.web.listener;

import io.github.chamikathereal.auction.web.websocket.WebSocketBroadcaster;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringReader;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/AuctionTopic"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
                @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "AuctionBidSubscription"),
                @ActivationConfigProperty(propertyName = "clientId", propertyValue = "AuctionDurableClient"),
                @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jms/AuctionConnectionFactory")
        }
)
public class BidListenerMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String content = ((TextMessage) message).getText();
            JsonObject bidJson = Json.createReader(new StringReader(content)).readObject();

            JsonObject wsMessage = Json.createObjectBuilder()
                    .add("itemId", bidJson.getInt("itemId"))
                    .add("bidderName", bidJson.getString("bidderName"))
                    .add("email", bidJson.getString("email"))
                    .add("amount", bidJson.getJsonNumber("amount").doubleValue())
                    .add("type", "newBid")
                    .build();

            System.out.printf("[MDB][RECEIVE] Bid JMS message: %s%n", bidJson);
            System.out.printf("[MDB][WEBSOCKET] Broadcasting to clients: %s%n", wsMessage);

            WebSocketBroadcaster.broadcast(wsMessage.toString());
        } catch (Exception e) {
            System.err.printf("[MDB][ERROR] %s%n", e.getMessage());
        }
    }
}

