package ru.lemar98.minesweeper.dto;

public class MinesTooMuchException extends RuntimeException {
    public MinesTooMuchException(int max) {
        super("количество мин должно быть не менее 1 и не более %s".formatted(max));
    }
}
