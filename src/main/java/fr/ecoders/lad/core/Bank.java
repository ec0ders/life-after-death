package fr.ecoders.lad.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Bank<T> {
  private final HashMap<T, Integer> content;
  
  public Bank() {
    this(Map.of());
  }
  
  public Bank(Map<T, Integer> content) {
    this.content = new HashMap<>(Map.copyOf(content));
  }
  
  public static <T> Bank<T> of(T t, int v) {
    return new Bank<>(Map.of(t, v));
  }
  
  public void addOne(T t) {
    add(t, 1);
  }
  
  public void add(T t, int v) {
    Objects.requireNonNull(t);
    content.merge(t, v, Integer::sum);
  }
  
  public int get(T t) {
    Objects.requireNonNull(t);
    return content.getOrDefault(t, 0);
  }
  
  public void removeOne(T t) {
    remove(t, 1);
  }
  
  public void remove(T t, int v) {
    Objects.requireNonNull(t);
    if (v > get(t)) {
      throw new IllegalArgumentException();
    }
    content.computeIfPresent(t, (k, i) -> (i == v) ? null : i - v);
  }
  
  public void addAll(Bank<T> other) {
    other.content.forEach(this::add);
  }
  
  public void removeAll(Bank<T> other) {
    if (other.content.entrySet()
      .stream()
      .anyMatch(e -> other.get(e.getKey()) > e.getValue())) {
      throw new IllegalArgumentException();
    }
    other.content.forEach(this::remove);
  }
  
  public Map<T, Integer> content() {
    return Map.copyOf(content);
  }
  
  public int size() {
    return content.values()
      .stream()
      .mapToInt(i -> i)
      .sum();
  }
  
  @Override
  public String toString() {
    return content.toString();
  }
}
