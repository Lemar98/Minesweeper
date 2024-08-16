package ru.lemar98.minesweeper.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lemar98.minesweeper.dto.GameNewRequest;
import ru.lemar98.minesweeper.dto.GameResponse;
import ru.lemar98.minesweeper.dto.GameTurnRequest;
import ru.lemar98.minesweeper.dto.MinesTooMuchException;
import ru.lemar98.minesweeper.services.GameService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MineSweeperController {
    private final GameService gameService;

    @Autowired
    public MineSweeperController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public ResponseEntity<GameResponse> newHandle(@Valid @RequestBody GameNewRequest request) {
        int max = request.getWidth() * request.getHeight() - 1;
        if (request.getMinesCount() > max) throw new MinesTooMuchException(max);
        return ResponseEntity.ok(gameService.create(request.getWidth(), request.getHeight(), request.getMinesCount()));
    }

    @PostMapping("/turn")
    public ResponseEntity<GameResponse> turnHandle(@RequestBody GameTurnRequest request) {
        return ResponseEntity.ok(gameService.openSlot(request.getGameId(), request.getX(), request.getY()));
    }
}
