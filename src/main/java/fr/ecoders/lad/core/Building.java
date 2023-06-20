package fr.ecoders.lad.core;

public sealed interface Building {

//  int defence();
//  int attack();
  
  String name();
  Bank<Supply> cost();
  
  Building WELL = new Supplier("Well", Bank.of(Supply.BASIC_CONSTRUCTION, 1), Bank.of(Supply.WATER, 1));
  
  Building BASIC_WATCH_TOWER = new Military("Watch tower", Bank.of(Supply.BASIC_CONSTRUCTION, 4), 0, 3);
  
  record Supplier(String name, Bank<Supply> cost, Bank<Supply> product) implements Building {}
  record Military(String name, Bank<Supply> cost, int defense, int offense) implements Building {}
}
