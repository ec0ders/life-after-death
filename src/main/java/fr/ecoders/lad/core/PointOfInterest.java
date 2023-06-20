package fr.ecoders.lad.core;

public sealed interface PointOfInterest {
  
  void search(GameState gameState);
  
  record Forest() implements PointOfInterest {
    @Override
    public void search(GameState gameState) {
    }
  }
  
  record River() implements PointOfInterest {
    
    @Override
    public void search(GameState gameState) {
    
    }
  }
  
  
}
