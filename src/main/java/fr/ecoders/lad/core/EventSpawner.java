package fr.ecoders.lad.core;

import java.util.Optional;
import java.util.Map;

public record EventSpawner(Map<Class<? extends Event>, Double> probabilities) {

  public Event spawn() {



    return new Event.None();
  }
  
  public static void main(String[] args) {
    System.out.println(Math.random());
    var t = new EventSpawner(Map.of(
      Event.Horde.class, 0.5,
      Event.CardSupplies.class, 0.4
    ));
  }
}
