package fr.ecoders.lad.console;

import fr.ecoders.lad.core.Card;
import fr.ecoders.lad.core.GameState;
import fr.ecoders.lad.core.Supply;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ConsoleDrawer {

  private final GameState gs;
  private final List<Card> cards;
  private final List<Card> researches;

  public ConsoleDrawer(GameState gs) {
    Objects.requireNonNull(gs);
    this.gs = gs;
    this.cards = toList(gs.cards());
    this.researches = toList(gs.research());
  }

  private <V> List<V> toList(Map<V, Integer> bank) {
    return bank.entrySet()
      .stream()
      .flatMap(entry -> IntStream.range(0, entry.getValue())
        .mapToObj(i -> entry.getKey()))
      .toList();
  }

  public void printState() {
    var survivedDays = gs.survivedDays();
    var buildings = gs.buildings()
      .size();
    var food = gs.supplies()
      .get(Supply.FOOD);
    var water = gs.supplies()
      .get(Supply.WATER);
    var cards = gs.cards()
      .size();
    System.out.printf(
      "Vous avez survécu %d jour(s).%nVous avez %d bâtiments(s), %f d'eau, %f de nourriture et %d cartes(s).%n",
      survivedDays,
      buildings,
      water,
      food,
      cards);
  }

  private <V, I> void printBank(Map<V, I> bank, Function<Map.Entry<V, I>, String> mapper) {
    var str = bank.entrySet()
      .stream()
      .map(mapper)
      .collect(Collectors.joining("\n\t", "\t", ""));
    System.out.println(str);
  }

  public void printSupplies() {
    var supplies = gs.supplies();
    if (supplies.isEmpty()) {
      System.out.println("Vous n'avez pas de ressources");
      return;
    }
    System.out.println("Vos ressources :");
    printBank(supplies, supply -> supply.getKey() + " : " + supply.getValue());
  }

  public void printCards() {
    if (cards.isEmpty()) {
      System.out.println("Vous n'avez pas de cartes");
      return;
    }
    System.out.println("Vos cartes :");
    var str = cards.stream()
      .map(Objects::toString)
      .collect(Collectors.joining("\n\t", "\t", ""));
    System.out.println(str);
  }

  public void printResearches() {
    if (researches.isEmpty()) {
      System.out.println("Aucune recherches disponibles");
      return;
    }
    System.out.println("Recherches disponibles :");
    var str = researches.stream()
      .map(Objects::toString)
      .collect(Collectors.joining("\n\t", "\t", ""));
    System.out.println(str);
  }

  public Card getCard(int index) {
    return cards.get(index);
  }

  public Card getResearch(int index) {
    return researches.get(index);
  }

}
