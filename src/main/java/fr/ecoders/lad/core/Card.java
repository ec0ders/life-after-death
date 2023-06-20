package fr.ecoders.lad.core;

public sealed interface Card {
  void apply(GameState gs);
  
  record AddBuilding(Building building) implements Card {
    @Override
    public void apply(GameState gs) {
      gs.addBuilding(building);
    }
  }
}
