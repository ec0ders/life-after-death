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
      if (!gs.cards().content().containsKey(card)) {
          throw new IllegalArgumentException();
      }
      switch (card) {
        case Card.AddBuilding addBuilding -> {
          if (!gs.supplies().containsAll(addBuilding.building().cost())) {
            throw new IllegalArgumentException();
          }
        }
      }
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
      poi.search(gs);
    }
  }

  record ResearchCard(GameState gs, Card card) implements Action {
    public ResearchCard {
      Objects.requireNonNull(gs);
      Objects.requireNonNull(card);
      if (!gs.research().content().containsKey(card)) {
        throw new IllegalArgumentException();
      }
    }

    @Override
    public void play() {
      gs.researchACard(card);
    }
  }

}
