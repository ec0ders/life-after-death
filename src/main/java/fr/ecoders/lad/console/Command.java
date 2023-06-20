package fr.ecoders.lad.console;

import java.util.Map;

public sealed interface Command {
  Map<String, Class<? extends Record>> COMMANDS = Map.of("playcard", PlayCard.class);
  
  record PlayCard(int cardIndex) implements Command {}
  
}
