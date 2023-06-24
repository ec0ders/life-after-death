package fr.ecoders.lad.core;

import java.util.Map;

public sealed interface PointOfInterest {
  
  void search(GameState gameState);
  
  record Forest() implements PointOfInterest {
    @Override
    public void search(GameState gameState) {
      var supplies = new Bank(Map.of(Supply.BASIC_CONSTRUCTION, 3, Supply.FOOD, 1));
      gameState.addSupplies(supplies);
    }
  }
  
  record River() implements PointOfInterest {
    @Override
    public void search(GameState gameState) {
      var supplies = Bank.of(Supply.WATER, 2);
      gameState.addSupplies(supplies);
    }
  }
}
