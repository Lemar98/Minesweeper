package ru.lemar98.minesweeper.exceptions;

public class CordsOutOfBounds extends MinesweeperException {
    public CordsOutOfBounds() {
        super("Координаты за пределами игрового поля");
    }
}
