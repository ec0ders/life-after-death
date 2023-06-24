package fr.ecoders.lad.core;

import fr.ecoders.lad.Utils;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameState {

  private Map<Supply, Double> supplies = Map.of();
  private Map<Card, Integer> cards = Map.of();
  private Map<Card, Integer> research = Map.of();
  private Map<Building, Integer> buildings = Map.of();
  private int maxResearchCards = 3;
  private int survivorCount = 0;
  private int woundedCount = 0;
  private int deathCount = 0;
  private int actionPerTurn = 1;
  private int survivedDays = 0;


  public void addBuilding(Building building) {
    Objects.requireNonNull(building);
    buildings = Utils.mergeMap(buildings, Map.of(building, 1), Integer::sum);
  }

  public int cardsCount() {
    return cards.size();
  }

  public boolean canPlayCard(Card card) {
    Objects.requireNonNull(card);
    if (!cards.containsKey(card)) {
      throw new IllegalStateException("the card " + card + " isn't in the gamestate");
    }
    return switch (card) {
      case Card.AddBuilding addBuilding -> addBuilding.building()
        .cost()
        .entrySet()
        .stream()
        .noneMatch(e -> supplies().getOrDefault(e.getKey(), 0.0) < e.getValue());
    };
  }

  public void addCard(Card card) {
    Objects.requireNonNull(card);
    cards = Utils.mergeMap(cards, Map.of(card, 1), Integer::sum);
  }

  public void removeCard(Card card) {
    Objects.requireNonNull(card);
    cards = Utils.mergeMap(cards, Map.of(card, 1), (i, j) -> (i == j) ? null : (i - j));
  }

  public void addSupplies(Map<Supply, Double> s) {
    Objects.requireNonNull(s);
    supplies = Utils.mergeMap(supplies, s, Double::sum);
  }

  public void removeSupplies(Map<Supply, Double> s) {
    Objects.requireNonNull(s);
    if (s.entrySet()
      .stream()
      .anyMatch(e -> supplies.get(e.getKey()) < e.getValue())) {
      throw new IllegalArgumentException("Not enough supplies to remove");
    }
    supplies = Utils.mergeMap(supplies, s, (i, j) -> (i == j) ? null : (i - j));
  }

  public Map<Card, Integer> cards() {
    return cards;
  }

  public void nextDay() {
    survivedDays++;
  }

  @Override
  public String toString() {
    return "GameState{\n\tsurvivedDays=%d\n\tbuildings=%s\n\tsupplies=%s\n\tcards=%s\n\tresearches=%s\n}".formatted(
      survivedDays,
      buildings,
      supplies,
      cards,
      research.entrySet()
        .stream()
        .map(Objects::toString)
        .collect(Collectors.joining("\n\t\t", "{\n\t\t", "}")));
  }

  public int actionPerTurn() {
    return actionPerTurn;
  }

  public boolean endTurn() {
    var foodConsumption = Math.ceil(survivorCount / 10.0);
    var waterConsumption = Math.ceil(survivorCount / 10.0);

    if (supplies.get(Supply.FOOD) < foodConsumption) {
      return true;
    }
    if (supplies.get(Supply.WATER) < waterConsumption) {
      return true;
    }
    removeSupplies(Map.of(Supply.FOOD, foodConsumption, Supply.WATER, foodConsumption));
    survivedDays++;

    for (var i = maxResearchCards - research.size(); i > 0; i--) {
      //TODO Math.random
      addResearch(Game.researchable.get((int) (Math.random() * Game.researchable.size())));
    }
    return false;
  }

  public int survivedDays() {
    return survivedDays;
  }

  public Map<Card, Integer> research() {
    return research;
  }

  public Map<Building, Integer> buildings() {
    return buildings;
  }

  public Map<Supply, Double> supplies() {
    return supplies;
  }

  public void addResearch(Card card) {
    Objects.requireNonNull(card);
    research = Utils.mergeMap(research, Map.of(card, 1), Integer::sum);
  }

  public void removeResearch(Card card) {
    Objects.requireNonNull(card);
    research = Utils.mergeMap(research, Map.of(card, 1), (i, j) -> (i == j) ? null : (i - j));
  }

  public void researchCard(Card card) {
    removeResearch(card);
    addCard(card);
  }
}
