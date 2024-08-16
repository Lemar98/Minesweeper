package ru.lemar98.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class GameTurnRequest {
    @JsonProperty("game_id")
    private UUID gameId;
    @JsonProperty("col")
    private int y;
    @JsonProperty("row")
    private int x;
}
