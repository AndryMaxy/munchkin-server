package com.andry.munchkin.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketResponse {
    private ObjectType objectType;
    private EventType eventType;
    private String payload;
}
