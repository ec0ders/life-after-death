package fr.ecoders.lad.util;

import fr.ecoders.lad.core.Event;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.function.Supplier;

public class ProbabilityMapper<T> implements Supplier<T> {

  private final Supplier<Double> randomSupplier;
  private Map<Range, T> probabilities = new HashMap<>();

  /**
   *
   * @param randomSupplier supplier of doubles between 0.0 inclusive and 1.0 exclusive
   * @param probabilities
   */
  public ProbabilityMapper(Supplier<Double> randomSupplier, Map<T, Double> probabilities) {
    Objects.requireNonNull(randomSupplier);
    Objects.requireNonNull(probabilities);
    this.randomSupplier = randomSupplier;

    var sum = probabilities.values()
      .stream()
      .mapToDouble(p -> p)
      .sum();
    if (sum == 0) {
      throw new IllegalArgumentException("the sum of all probabilities can't be 0");
    }

    double cum = 0;
    for (Map.Entry<T, Double> e : probabilities.entrySet()) {
      var percentage = e.getValue() / sum;
      this.probabilities.put(new Range(cum, cum + percentage), e.getKey());
      cum += percentage;
    }
  }

  private record Range(
    double start,
    double end) {
    public boolean has(double d) {
      return d >= start && d < end;
    }
  }

  public T get() {
    var random = randomSupplier.get();
    if (random < 0.0 || random >= 1.0) {
      throw new IllegalStateException("Random value must be in the range [0.0, 1.0[");
    }
    return probabilities.entrySet()
      .stream()
      .filter(p -> p.getKey()
        .has(random))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("le dev est une bite avec les random"))
      .getValue();
  }

  public static void main(String[] args) {
    var g = new ProbabilityMapper<>(Math::random, Map.of(
      Event.None.class, 0.1,
      Event.Horde.class, 0.5,
      Event.CardSupplies.class, 0.4
    ));
  }
}
