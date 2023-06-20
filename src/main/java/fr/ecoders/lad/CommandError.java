package fr.ecoders.lad;

public sealed interface CommandError {
    record InvalidInputError(String details) implements CommandError {
    }
}
