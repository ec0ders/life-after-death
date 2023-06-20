package fr.ecoders.lad.action;

import fr.ecoders.lad.core.Card;
import fr.ecoders.lad.core.GameState;
import fr.ecoders.lad.core.PointOfInterest;

import java.util.Objects;

public sealed interface Action {
  
  void play();
  
  record PlayCard(GameState gs, Card card) implements Action {
    public PlayCard {
      Objects.requireNonNull(gs);
      Objects.requireNonNull(card);
    }
    
    @Override
    public void play() {
      gs.removeCard(card);
      switch (card) {
        case Card.AddBuilding addBuilding -> {
          gs.addBuilding(addBuilding.building());
        }
      }
    }
  }
  
  record SearchSupply(GameState gs, PointOfInterest poi) implements Action {
    public SearchSupply {
      Objects.requireNonNull(gs);
      Objects.requireNonNull(poi);
    }
    
    @Override
    public void play() {
    
    }
  }
  
  record ResearchCard(GameState gs) implements Action {
    public ResearchCard {
      Objects.requireNonNull(gs);
    }
    
    @Override
    public void play() {
    
    }
  }
  
}
