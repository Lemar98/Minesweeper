package ru.lemar98.minesweeper.dto;

import ru.lemar98.minesweeper.exceptions.MinesweeperException;

public class MinesTooMuchException extends MinesweeperException {
    public MinesTooMuchException(int max) {
        super("количество мин должно быть не менее 1 и не более %s".formatted(max));
    }
}
