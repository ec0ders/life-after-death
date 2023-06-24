package fr.ecoders.lad.core;

public sealed interface Building {
    //  int defence();
//  int attack();
  
  String name();
  Bank<Supply> cost();

  Building BASIC_FARM = new Supplier("Farm", Bank.of(Supply.BASIC_CONSTRUCTION, 3), Bank.of(Supply.FOOD, 1));
  Building WELL = new Supplier("Well", Bank.of(Supply.BASIC_CONSTRUCTION, 5), Bank.of(Supply.WATER, 1));
  Building BASIC_WATCH_TOWER = new Military("Watch tower", Bank.of(Supply.BASIC_CONSTRUCTION, 4), 0, 3);
  Building BASIC_DEFENSE_WALL = new Military("Defense wall", Bank.of(Supply.BASIC_CONSTRUCTION, 6), 3, -1);

  record Supplier(String name, Bank<Supply> cost, Bank<Supply> product) implements Building {}
  record Military(String name, Bank<Supply> cost, int defense, int offense) implements Building {}
}
