package ru.lemar98.minesweeper.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lemar98.minesweeper.exceptions.CordsOutOfBounds;
import ru.lemar98.minesweeper.exceptions.SlotAlreadyOpenException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
public class Board {
    private int width;
    private int height;
    private int minesCount;
    private boolean firstMove;
    private List<Slot> slots;

    public Board(int width, int height, int minesCount) {
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.slots = new ArrayList<>();
        this.firstMove = true;
        initBoard();
    }

    public Slot getSlot(int y, int x) {
        return slots.stream()
                .filter(slot -> slot.getY() == y && slot.getX() == x)
                .findAny()
                .orElseThrow(CordsOutOfBounds::new);
    }

    public void unlockAllSlots() {
        slots.forEach(slot -> slot.setOpen(true));
    }

    public boolean openCell(int y, int x) {
        Slot slot = getSlot(y, x);
        if (slot.isOpen()) {
            throw new SlotAlreadyOpenException();
        }

        if (firstMove && slot.isMine()) {
            relocateMine(y, x);
            slot.setMine(false);
            slot.setMinesNearby(calculateCountNearbyMines(y, x));
        }

        firstMove = false;
        slot.setOpen(true);
        if (slot.isMine()) {
            return true;
        }

        if (slot.getMinesNearby() == 0) {
            openAdjacentCells(y, x);
        }
        return false;
    }

    private void relocateMine(int y, int x) {
        Slot mine = getSlot(y ,x);
        for (Slot slot : slots) {
            if (!slot.isMine() && !mine.equals(slot)) {
                slot.setMine(true);
                calculateNearby();
                return;
            }
        }
    }

    private void openAdjacentCells(int y, int x) {
        for (int stepX = -1; stepX <= 1; stepX++) {
            for (int stepY = -1; stepY <= 1; stepY++) {
                int nearbyX = x + stepX;
                int nearbyY = y + stepY;
                if (nearbyX >= 0 && nearbyY >= 0 && nearbyX < width && nearbyY < height) {
                    Slot nearbySlot = getSlot(nearbyY, nearbyX);
                    if (!nearbySlot.isOpen()) {
                        openCell(nearbyY, nearbyX);
                    }
                }
            }
        }
    }

    public boolean isBoardCompleted() {
        return slots.stream().noneMatch(slot -> !slot.isMine() && !slot.isOpen());
    }

    private void initBoard() {
        createSlots();
        fillByMines();
        calculateNearby();
    }

    private void createSlots() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                slots.add(Slot.builder().y(y).x(x).build());
            }
        }
    }

    private void fillByMines() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < minesCount; i++) {
            int y, x;
            do {
                y = random.nextInt(height);
                x = random.nextInt(width);
            } while (getSlot(y, x).isMine());
            getSlot(y, x).setMine(true);
        }
    }

    private void calculateNearby() {
        slots.stream()
                .filter(slot -> !slot.isMine())
                .forEach(slot -> slot.setMinesNearby(calculateCountNearbyMines(slot.getY(), slot.getX())));
    }

    private int calculateCountNearbyMines(int slotY, int slotX) {
        int minesCount = 0;
        for (int stepX = -1; stepX <= 1; stepX++) {
            for (int stepY = -1; stepY <= 1; stepY++) {
                int nearbyX = slotX + stepX;
                int nearbyY = slotY + stepY;
                if (nearbyX >= 0 && nearbyY >= 0 && nearbyX < width && nearbyY < height
                        && (getSlot(nearbyY, nearbyX).isMine())) {
                    minesCount++;
                }
            }
        }
        return minesCount;
    }
}
