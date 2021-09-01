package com.andry.munchkin.controller;

import com.andry.munchkin.dto.Player;
import com.andry.munchkin.dto.PlayerRequest;
import com.andry.munchkin.dto.Views;
import com.andry.munchkin.websocket.EventType;
import com.andry.munchkin.websocket.ObjectType;
import com.andry.munchkin.websocket.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private List<Player> players = new ArrayList<>();

    private BiConsumer<EventType, Player> sender;

    @Autowired
    public PlayerController(WebSocketSender webSocketSender) {
        this.sender = webSocketSender.getSender(ObjectType.PLAYER, Views.Full.class);
    }

    @GetMapping("/all")
    public List<Player> getAll() {
        return players;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Player> get(@PathVariable String name) {
        Player player = players.stream()
                .filter(plr -> plr.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No player"));
        return ResponseEntity.ok(player);
    }

    @PostMapping("/{name}")
    public ResponseEntity<Player> create(@PathVariable String name) {
        Player player = new Player(name);
        players.add(player);
        sender.accept(EventType.LOGIN, player);
        return ResponseEntity.ok(player);
    }

    @PutMapping
    public ResponseEntity<Player> update(@RequestBody PlayerRequest request) {
        Player player = players.stream()
                .filter(plr -> plr.getName().equals(request.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No player"));
        player.setLevel(request.getLevel());
        player.setBonus(request.getBonus());

        sender.accept(EventType.UPDATE, player);

        return ResponseEntity.ok(player);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable String name) {
        players = players.stream()
                .filter(player -> !player.getName().equals(name))
                .collect(Collectors.toList());
        sender.accept(EventType.DELETE, null);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Object> deleteAll() {
        players.clear();
        sender.accept(EventType.DELETE, null);
        return ResponseEntity.ok().build();
    }
}
