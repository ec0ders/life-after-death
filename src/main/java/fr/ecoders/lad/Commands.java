package fr.ecoders.lad;

import fr.ecoders.lad.action.EndTurn;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Commands {
    // TODO the user should have descriptive error messages on invalid inputs
    public static final Map<String, BiFunction<Scanner, GameState, Optional<Consumer<GameState>>>> COMMANDS = Map.of(
            "add-building", Commands::addBuilding,
            "play-card", Commands::playCard,
            "end-turn", Commands::endTurn,
            "nothing", (sc, gameState) -> Optional.of(g -> {
            }),
            "invalid", (sc, gameState) -> Optional.empty()
    );

    private static Optional<Consumer<GameState>> addBuilding(Scanner sc, GameState gameState) {
        return Optional.of((GameState g) -> g.addBuilding(new Building()));
    }

    private static Optional<Consumer<GameState>> playCard(Scanner sc, GameState gameState) {
        if (!sc.hasNextInt()) {
            return Optional.empty();
        }
        var index = sc.nextInt();

        if (index < 0 || index >= gameState.cardsCount()) {
            return Optional.empty();
        }
        return Optional.of(gameState.getCard(index).action());
    }

    private static Optional<Consumer<GameState>> endTurn(Scanner sc, GameState gameState) {
        return Optional.of(new EndTurn());
    }
}
