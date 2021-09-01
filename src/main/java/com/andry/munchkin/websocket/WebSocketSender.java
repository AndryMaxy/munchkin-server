package com.andry.munchkin.websocket;

import com.andry.munchkin.dto.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WebSocketSender {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class<? extends Views> view) {
        ObjectWriter writer = objectMapper.setConfig(objectMapper.getSerializationConfig()).writerWithView(view);

        return (eventType, obj) -> {
            String value = null;

            try {
                value = writer.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Cant convert object to string.");
            }

            template.convertAndSend("/topic/activity", new WebSocketResponse(objectType, eventType, value));
        };
    }
}
