package ru.lemar98.minesweeper.exceptions;

public class GameNotFoundException extends MinesweeperException {
    public GameNotFoundException() {
        super("Игра с таким ID не найдена");
    }
}
