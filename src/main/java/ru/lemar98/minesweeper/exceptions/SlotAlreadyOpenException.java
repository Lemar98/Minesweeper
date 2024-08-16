package ru.lemar98.minesweeper.exceptions;

public class SlotAlreadyOpenException extends MinesweeperException {
    public SlotAlreadyOpenException() {
        super("Эта ячейка уже открыта!");
    }
}
