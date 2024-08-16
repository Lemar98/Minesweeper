package ru.lemar98.minesweeper.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {
    private boolean mine = false;
    private boolean open = false;
    private int minesNearby = 0;
    private int y;
    private int x;
}
