package ru.lemar98.minesweeper.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@RedisHash(value = "Game", timeToLive = 300)
@NoArgsConstructor
public class Game {
    @Id
    private UUID id;
    private boolean completed;
    private Board board;

    public Game(Board board) {
        this.id = UUID.randomUUID();
        this.board = board;
        this.completed = false;
    }
}
