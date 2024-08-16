package ru.lemar98.minesweeper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lemar98.minesweeper.dto.GameResponse;
import ru.lemar98.minesweeper.exceptions.GameAlreadyEndException;
import ru.lemar98.minesweeper.exceptions.GameNotFoundException;
import ru.lemar98.minesweeper.models.Board;
import ru.lemar98.minesweeper.models.Game;
import ru.lemar98.minesweeper.repositories.GameRepository;

import java.util.UUID;

@Service
public class GameService {
    private final GameRepository repository;

    @Autowired
    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public GameResponse create(int width, int height, int minesCount) {
        Board board = new Board(width, height, minesCount);
        Game game = new Game(board);
        repository.save(game);
        return new GameResponse(game);
    }

    public GameResponse openSlot(UUID gameUuid, int y, int x) {
        Game game = repository.findById(gameUuid).orElseThrow(GameNotFoundException::new);
        if (game.isCompleted()) throw new GameAlreadyEndException();

        Board board = game.getBoard();
        boolean hitMine = board.openCell(y, x);

        game.setCompleted(hitMine || board.isBoardCompleted());
        if (game.isCompleted()) board.unlockAllSlots();

        repository.save(game);
        return new GameResponse(game, hitMine);
    }
}
