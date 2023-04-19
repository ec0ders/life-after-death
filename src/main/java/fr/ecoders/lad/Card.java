package fr.ecoders.lad;

import java.util.Objects;
import java.util.function.Consumer;

public record Card(Consumer<GameState> action) {
    public Card {
        Objects.requireNonNull(action);
    }
}
