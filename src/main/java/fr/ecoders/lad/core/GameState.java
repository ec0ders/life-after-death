package fr.ecoders.lad.core;

import java.util.Objects;
import java.util.stream.Collectors;

public class GameState {

  private Bank<Supply> supplies = new Bank<>();
  private Bank<Card> cards = new Bank<>();
  private Bank<Card> research = new Bank<>();
  private Bank<Building> buildings = new Bank<>();
  private int maxResearchCards = 3;
  private int survivorCount = 0;
  private int woundedCount = 0;
  private int deathCount = 0;
  private int actionPerTurn = 1;
  private int survivedDays = 0;


  public void addBuilding(Building building) {
    Objects.requireNonNull(building);
    buildings = buildings.addOne(building);
  }

  public int cardsCount() {
    return cards.size();
  }

  public void addCard(Card c) {
    Objects.requireNonNull(c);
    cards = cards.addOne(c);
  }

  public void removeCard(Card c) {
    Objects.requireNonNull(c);
    cards = cards.removeOne(c);
  }

  public void addSupplies(Bank<Supply> s) {
    Objects.requireNonNull(s);
    supplies = supplies.addAll(s);
  }

  public Bank<Card> cards() {
    return Bank.copyOf(cards);
  }

  public GameStateView view() {
    return new GameStateView(cards.content(), supplies.content(), buildings.content());
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
      research.content()
        .entrySet()
        .stream()
        .map(Objects::toString)
        .collect(Collectors.joining("\n\t\t", "{\n\t\t", "}")));
  }

  public int actionPerTurn() {
    return actionPerTurn;
  }

  public boolean endTurn() {
    var foodConsumption = (int) Math.ceil(survivorCount / 10.0);
    var waterConsumption = (int) Math.ceil(survivorCount / 10.0);

    if (supplies.get(Supply.FOOD) < foodConsumption) {
      return true;
    }
    if (supplies.get(Supply.WATER) < waterConsumption) {
      return true;
    }
    supplies = supplies.remove(Supply.FOOD, foodConsumption);
    supplies = supplies.remove(Supply.FOOD, waterConsumption);
    survivedDays++;

    for (var i = maxResearchCards - research.size(); i > 0; i--) {
      //TODO Math.random
      research = research.addOne(Game.researchable.get((int) (Math.random() * Game.researchable.size())));
    }
    return false;
  }

  public int survivedDays() {
    return survivedDays;
  }

  public Bank<Card> research() {
    return Bank.copyOf(research);
  }

  public Bank<Supply> supplies() {
    return Bank.copyOf(supplies);
  }

  public void researchACard(Card card) {
    research = research.removeOne(card);
    cards = cards.addOne(card);
  }
}
