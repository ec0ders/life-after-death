package fr.ecoders.lad.core;

import java.util.Map;

public sealed interface Building {
    //  int defence();
//  int attack();
  
  String name();
  Map<Supply, Double> cost();

  Building BASIC_FARM = new Supplier("Farm", Map.of(Supply.BASIC_CONSTRUCTION, 3.0), Map.of(Supply.FOOD, 1.0));
  Building WELL = new Supplier("Well", Map.of(Supply.BASIC_CONSTRUCTION, 5.0), Map.of(Supply.WATER, 1.0));
  Building BASIC_WATCH_TOWER = new Military("Watch tower", Map.of(Supply.BASIC_CONSTRUCTION, 4.0), 0, 3);
  Building BASIC_DEFENSE_WALL = new Military("Defense wall", Map.of(Supply.BASIC_CONSTRUCTION, 6.0), 3, -1);

  record Supplier(String name, Map<Supply, Double> cost, Map<Supply, Double> product) implements Building {}
  record Military(String name, Map<Supply, Double> cost, int defense, int offense) implements Building {}
}
