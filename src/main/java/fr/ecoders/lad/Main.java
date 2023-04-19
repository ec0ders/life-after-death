package fr.ecoders.lad;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans Life After Death");

        var gameState = new GameState();

        try (var scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                var command = scanner.next();

                Optional<Consumer<GameState>> a = switch (command) {
                    case "add-building" -> Optional.of((g) -> g.addBuilding(new Building()));
                    case "play-card" -> {
                        if (!scanner.hasNextInt()) {
                            yield Optional.empty();
                        }
                        var index = scanner.nextInt();

                        if (index < 0 || index >= gameState.cardsCount()) {
                            yield Optional.empty();
                        }
                        yield Optional.of(gameState.getCard(index).action());
                    }
                    case "n" -> Optional.of((GameState g) -> {
                    });
                    default -> Optional.empty();
                };

                if (a.isEmpty()) {
                    System.out.println("Commande inconnue");
                    continue;
                }

                a.get().accept(gameState);
                System.out.println(gameState);
            }
        }
    }
}