package fr.ecoders.lad.console;

import java.util.Map;

public sealed interface Command {
  Map<String, Class<? extends Record>> COMMANDS = Map.of("playcard",
    PlayCard.class,
    "searchpoi",
    SearchPoi.class,
    "research",
    ResearchCard.class,
    "print",
    Print.class);

  record PlayCard(int cardIndex) implements Command {}

  record SearchPoi(String poiName) implements Command {}

  record ResearchCard(int cardIndex) implements Command {}

  record Print(Info info) implements Command {
    enum Info {
      STATE,
      CARDS,
      RESEARCHES,
      SUPPLIES
    }
  }

}
