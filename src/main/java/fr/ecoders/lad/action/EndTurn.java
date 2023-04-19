package fr.ecoders.lad.action;

import fr.ecoders.lad.GameState;

import java.util.function.Consumer;

public record EndTurn() implements Consumer<GameState> {
    @Override
    public void accept(GameState gameState) {
        gameState.nextDay();
    }
}
