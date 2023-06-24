package fr.ecoders.lad.util;

import java.util.Map;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProbabilityMapperTest {
  @Test
  void preconditions() {
    assertThrows(NullPointerException.class, () -> new ProbabilityMapper<>(null, Map.of("a", 1.0)));
    assertThrows(NullPointerException.class, () -> new ProbabilityMapper<>(Math::random, null));
    assertThrows(IllegalArgumentException.class, () -> new ProbabilityMapper<>(Math::random, Map.of("a",
      0.0, "b", 0.0)));
  }
  @Test
  void testGetWithOneProbability(){
    assertEquals("a", new ProbabilityMapper<>(() -> 1.0, Map.of("a", 1.0)).get());
    assertEquals("a", new ProbabilityMapper<>(() -> 1.0, Map.of("a", 0.5)).get());
  }

  @Test
  void testGet() {
    var probabilities = new TreeMap<>(Map.of("a", 0.5, "b", 0.5));
    assertEquals("a", new ProbabilityMapper<>(() -> 0.2, probabilities).get());
    assertEquals("b", new ProbabilityMapper<>(() -> 0.7, probabilities).get());
  }

}