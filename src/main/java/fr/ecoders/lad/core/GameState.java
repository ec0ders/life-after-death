package fr.ecoders.lad.core;

import java.util.Map;
import java.util.Objects;

public class GameState {
  
  private final Bank<Supply> supplies = new Bank<>();
  private final Bank<Card> cards = new Bank<>();
  private final Bank<Building> buildings = new Bank<>();
  private int population = 0;
  private int wounded = 0;
  private int death = 0;
  private int actionPerTurn = 1;
  private int survivedDays = 0;
  
  public void addBuilding(Building building) {
    Objects.requireNonNull(building);
    buildings.addOne(building);
  }
  
  public int cardsCount() {
    return cards.size();
  }
  
  public void addCard(Card c) {
    Objects.requireNonNull(c);
    cards.addOne(c);
  }
  
  public void removeCard(Card c) {
    Objects.requireNonNull(c);
    cards.removeOne(c);
  }
  
  public Map<Card, Integer> cards() {
    return cards.content();
  }
  
  public GameStateView view() {
    return new GameStateView(cards.content(), supplies.content(), buildings.content());
  }
  
  public void nextDay() {
    survivedDays++;
  }
  
  @Override
  public String toString() {
    return "GameState{" +
      "buildings=" + buildings +
      ", cards=" + cards +
      ", survivedDays=" + survivedDays +
      '}';
  }
  
  public int actionPerTurn() {
    return actionPerTurn;
  }
  
  public boolean endTurn() {
    var foodConsumption = (int) Math.ceil(population / 10.0);
    var waterConsumption = (int) Math.ceil(population / 10.0);
    
    if (supplies.get(Supply.FOOD) < foodConsumption) {
      return true;
    }
    if (supplies.get(Supply.WATER) < waterConsumption) {
      return true;
    }
    supplies.remove(Supply.FOOD, foodConsumption);
    supplies.remove(Supply.FOOD, waterConsumption);
    survivedDays++;
    return false;
  }
  
  public int survivedDays() {
    return survivedDays;
  }
}
