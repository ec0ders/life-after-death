package fr.ecoders.lad.core;

import fr.ecoders.lad.core.enemy.Zombie;

import java.util.List;

public sealed interface Event {
  
  void impact(GameState gameState);
  
  record Horde(List<Zombie> zombies) implements Event {
    public void impact(GameState gameState) {
    
    }
  }
  record CardSupplies() implements Event {
    public void impact(GameState gameState) {
    
    }
  }
  record SuppliesDrop() implements Event {
    public void impact(GameState gameState) {
    
    }
  }
  record RedMoon() implements Event {
    public void impact(GameState gameState) {
    
    }
  }
}
