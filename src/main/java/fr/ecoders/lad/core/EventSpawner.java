package fr.ecoders.lad.core;

import java.util.Optional;
import java.util.Map;

public record EventSpawner(Map<Class<? extends Event>, Double> probabilities) {

  public Optional<Event> spawn() {
    return Optional.empty();
  }
  
  public static void main(String[] args) {
    var t = new EventSpawner(Map.of(
      Event.Horde.class, 0.5,
      Event.CardSupplies.class, 0.4
    ));
  }
}
