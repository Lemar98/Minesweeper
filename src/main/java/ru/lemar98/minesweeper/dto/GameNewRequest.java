package ru.lemar98.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GameNewRequest {
    @Min(value = 1, message = "ширина поля должна быть не менее 2 и не более 30")
    @Max(value = 30, message = "ширина поля должна быть не менее 2 и не более 30")
    private int width;
    @Min(value = 1, message = "высота поля должна быть не менее 2 и не более 30")
    @Max(value = 30, message = "высота поля должна быть не менее 2 и не более 30")
    private int height;
    @Min(value = 1, message = "количество мин должно быть не менее 1")
    @JsonProperty("mines_count")
    private int minesCount;
}
