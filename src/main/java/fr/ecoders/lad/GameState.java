package fr.ecoders.lad;

import java.util.ArrayList;
import java.util.Objects;

public class GameState {

    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Card> cards = new ArrayList<>();
    private int survivedDays = 0;

    public void addBuilding(Building building) {
        Objects.requireNonNull(building);
        buildings.add(building);
    }

    public int cardsCount() {
        return cards.size();
    }

    public void addCard(Card c) {
        Objects.requireNonNull(c);
        cards.add(c);
    }

    public void removeCard(int i) {
        Objects.checkIndex(i, cards.size());
        cards.remove(i);
    }

    public Card getCard(int i) {
        Objects.checkIndex(i, cards.size());
        return cards.get(i);
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
}
