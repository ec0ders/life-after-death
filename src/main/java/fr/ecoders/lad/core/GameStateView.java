package fr.ecoders.lad.core;

import java.util.Map;
import java.util.Objects;

public record GameStateView(
  Map<Card, Integer> cards,
  Map<Supply, Integer> supplies,
  Map<Building, Integer> buildings
) {
  
  public GameStateView {
    Objects.requireNonNull(cards);
    Objects.requireNonNull(supplies);
    Objects.requireNonNull(buildings);
  }
}
