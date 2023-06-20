package fr.ecoders.lad;

import fr.ecoders.lad.action.EndTurn;
import fr.ecoders.lad.util.Response;

import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Commands {
    // TODO the user should have descriptive error messages on invalid inputs
    public static final Map<String, BiFunction<Scanner, GameState, Response<Consumer<GameState>, CommandError>>> COMMANDS = Map.of(
            "add-building", Commands::addBuilding,
            "play-card", Commands::playCard,
            "end-turn", Commands::endTurn,
            "nothing", (sc, gameState) -> Response.ofValue(g -> {
            }),
            "invalid", (sc, gameState) -> Response.ofError(new CommandError.InvalidInputError(""))
    );

    private static Response<Consumer<GameState>, CommandError> addBuilding(Scanner sc, GameState gameState) {
        return Response.ofValue((GameState g) -> g.addBuilding(new Building()));
    }

    private static Response<Consumer<GameState>, CommandError> playCard(Scanner sc, GameState gameState) {
        if (!sc.hasNextInt()) {
            return Response.ofError(new CommandError.InvalidInputError("no card number given"));
        }
        var index = sc.nextInt();

        if (index < 0 || index >= gameState.cardsCount()) {
            return Response.ofError(new CommandError.InvalidInputError("invalid card number"));
        }
        return Response.ofValue(gameState.getCard(index).action());
    }

    private static Response<Consumer<GameState>, CommandError> endTurn(Scanner sc, GameState gameState) {
        return Response.ofValue(new EndTurn());
    }
}
