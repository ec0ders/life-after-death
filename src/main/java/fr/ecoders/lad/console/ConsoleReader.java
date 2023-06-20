package fr.ecoders.lad.console;

import fr.ecoders.lad.Utils;
import fr.ecoders.lad.action.Action;
import fr.ecoders.lad.core.GameState;
import fr.ecoders.lad.core.GameStateView;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleReader {
  private static final Logger LOGGER = Logger.getLogger(ConsoleReader.class.getName());
  private Scanner scanner;
  
  public ConsoleReader(Scanner scanner) {
    Objects.requireNonNull(scanner);
    this.scanner = scanner;
  }
  
  private Object parseCommandArgument(RecordComponent recordComponent) {
    var argType = recordComponent.getType();
    if (!scanner.hasNext()) {
      return null;
    }
    if (argType.equals(String.class)) {
      return scanner.next();
    } else if (argType.equals(int.class)) {
      return Integer.parseInt(scanner.next());
    } else if (argType.equals(long.class)) {
      return Long.parseLong(scanner.next());
    } else if (argType.equals(double.class)) {
      return Double.parseDouble(scanner.next());
    } else {
      throw new IllegalArgumentException("An argument of a command must be a primitive or a String.");
    }
  }
  
  private <T extends Record> T parseRecord(Class<T> type) {
    var components = type.getRecordComponents();
    var fields = Arrays.stream(components)
      .map(this::parseCommandArgument)
      .toList();
    if (fields.stream()
      .anyMatch(Objects::isNull)) {
      return null;
    }
    var constructor = Utils.canonicalConstructor(type, components);
    return Utils.newInstance(constructor, fields.toArray());
  }
  
  /**
   * Read the user's input until a valid action is given. Like "playcard 0".
   *
   * @param gameState current state of the game before the user does any action.
   * @return the user's desired action
   */
  public Action getAction(GameState gs) {
    var commands = Command.COMMANDS;
    
    while (true) {
      if (!scanner.hasNext()) {
        LOGGER.warning("The scanner is empty");
        throw new AssertionError();
      }
      var cmdName = scanner.next();
      if (!commands.containsKey(cmdName)) {
        System.out.println("Commande inconnue");
        continue;
      }
      var cmdType = commands.get(cmdName);
      var cmd = (Command) parseRecord(cmdType);
      if (cmd == null) {
        System.out.println("Une erreur est survenu dans la commande");
        continue;
      }
      
      
      return switch (cmd) {
        case Command.PlayCard playCard -> new Action.PlayCard(gs, gs.cards().keySet().stream().toList().get(playCard.cardIndex()));
      };
    }
  }
}
