package ru.lemar98.minesweeper.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.lemar98.minesweeper.models.Game;

import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
}
