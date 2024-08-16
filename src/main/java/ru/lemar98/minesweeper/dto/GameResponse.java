package ru.lemar98.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import ru.lemar98.minesweeper.models.Board;
import ru.lemar98.minesweeper.models.Game;
import ru.lemar98.minesweeper.models.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonPropertyOrder({"game_id", "width", "height", "minesCount", "field", "completed"})
public class GameResponse {
    @JsonProperty("game_id")
    private UUID gameId;
    private int width;
    private int height;
    private int minesCount;
    private List<List<String>> field;
    private boolean completed;

    public GameResponse(Game game, boolean lose) {
        this.gameId = game.getId();
        this.completed = game.isCompleted();
        Board board = game.getBoard();
        this.width = board.getWidth();
        this.height = board.getHeight();
        this.minesCount = board.getMinesCount();
        this.field = toField(board, lose);
    }

    public GameResponse(Game game) {
        this(game, false);
    }

    private List<List<String>> toField(Board board, boolean lose) {
        List<List<String>> field = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            List<String> row = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                Slot slot = board.getSlot(y, x);
                if (slot.isOpen() && slot.isMine()) {
                    row.add(lose ? "X" : "M");
                } else if (!slot.isOpen()) {
                    row.add(" ");
                } else {
                    row.add(String.valueOf(slot.getMinesNearby()));
                }
            }
            field.add(row);
        }
        return field;
    }
}
