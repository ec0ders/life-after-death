package fr.ecoders.lad.core;

import fr.ecoders.lad.action.Action;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public record Game(Function<GameState, Action> actionSupplier) {

  public static final List<Card> researchable = List.of(
    new Card.AddBuilding(Building.BASIC_FARM),
    new Card.AddBuilding(Building.WELL),
    new Card.AddBuilding(Building.BASIC_WATCH_TOWER),
    new Card.AddBuilding(Building.BASIC_DEFENSE_WALL));

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
