package com.mintonomous.handlers;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintonomous.model.PlantDataPayload;

@Component
public class PlantDataMessageHandler implements MessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PlantDataMessageHandler.class);

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            LOG.info("Received Message: " + message.getPayload().toString());

            PlantDataPayload payload = mapper.readerFor(PlantDataPayload.class).readValue(message.getPayload().toString());
            //TODO map payload to PlantData object
        } catch (IOException e) {
           LOG.error("Exception happened: {} , {}", e.getMessage(), e);
        }
    }
}