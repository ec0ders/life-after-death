package fr.ecoders.lad.core;

import fr.ecoders.lad.action.Action;

import java.util.Objects;
import java.util.function.Function;

public record Game(Function<GameState, Action> actionSupplier) {
  
  public Game {
    Objects.requireNonNull(actionSupplier);
  }
  
  public void run(GameState gameState) {
    for (var actionCountLeft = gameState.actionPerTurn(); actionCountLeft > 0; actionCountLeft--) {
      var action = actionSupplier.apply(gameState);
      action.play();
    }
    if (gameState.endTurn()) {
      System.out.println("Your camp survived " + gameState.survivedDays());
      return;
    }
  }
}
