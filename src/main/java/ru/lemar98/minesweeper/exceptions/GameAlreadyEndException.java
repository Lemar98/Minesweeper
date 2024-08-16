package ru.lemar98.minesweeper.exceptions;

public class GameAlreadyEndException extends MinesweeperException {
    public GameAlreadyEndException() {
        super("Игра завершена");
    }
}
