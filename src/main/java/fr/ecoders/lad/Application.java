package fr.ecoders.lad;

import fr.ecoders.lad.action.Actions;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        System.out.println("Bienvenue dans Life After Death");

        var gameState = new GameState();
        gameState.addCard(new Card(Actions.buildingCardAction(0, new Building())));

        try (var scanner = new Scanner(System.in)) {
            while (true) {
                getAction(scanner, gameState).accept(gameState);
                System.out.println(gameState);
            }
        }
    }

    private static Consumer<GameState> getAction(Scanner s, GameState gameState) {
        Optional<Consumer<GameState>> action = Optional.empty();

        var commands = Commands.COMMANDS;

        while (action.isEmpty()) {
            if (!s.hasNext()) {
                LOGGER.warning("The scanner is empty");
                throw new AssertionError();
            }
            var commandString = s.next();
            if (!commands.containsKey(commandString)) {
                System.out.println("Commande inconnue");
                continue;
            }
            var command = commands.get(commandString);
            action = command.apply(s, gameState);
        }

        return action.get();
    }
}