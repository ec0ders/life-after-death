package fr.ecoders.lad.main;

import fr.ecoders.lad.console.ConsoleReader;
import fr.ecoders.lad.core.*;

import java.util.Scanner;
import java.util.logging.Logger;

public class Application {
  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
  
  public static void main(String[] args) {
    System.out.println("Bienvenue dans Life After Death");
    
    var gameState = new GameState();
    gameState.addCard(new Card.AddBuilding(Building.WELL));
    
    try (var scanner = new Scanner(System.in)) {
      var consoleReader = new ConsoleReader(scanner);
      var game = new Game(consoleReader::getAction);
      while (true) {
        game.run(gameState);
      }
    }
  }
  
}