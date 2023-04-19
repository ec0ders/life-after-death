package fr.ecoders.lad.action;

import fr.ecoders.lad.Building;
import fr.ecoders.lad.GameState;

import java.util.Objects;
import java.util.function.Consumer;

public class Actions {
    public static Consumer<GameState> removeCard(int i) {
        return gameState -> gameState.removeCard(i);
    }

    public static Consumer<GameState> addBuilding(Building b) {
        Objects.requireNonNull(b);
        return g -> g.addBuilding(b);
    }

    public static Consumer<GameState> buildingCardAction(int index, Building b) {
        return removeCard(index).andThen(addBuilding(b));
    }
}
