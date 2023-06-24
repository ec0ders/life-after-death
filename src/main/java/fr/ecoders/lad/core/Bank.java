package fr.ecoders.lad.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Bank<T> {
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

  public static <V> Bank<V> copyOf(Bank<V> bank) {
    return new Bank<>(bank.content);
  }

  public Bank<T> addOne(T t) {
    add(t, 1);
    return this;
  }

  public Bank<T> add(T t, int v) {
    Objects.requireNonNull(t);
    content.merge(t, v, Integer::sum);
    return this;
  }

  public int get(T t) {
    Objects.requireNonNull(t);
    return content.getOrDefault(t, 0);
  }

  public Bank<T> removeOne(T t) {
    remove(t, 1);
    return this;
  }

  public Bank<T> remove(T t, int v) {
    Objects.requireNonNull(t);
    if (v > get(t)) {
      throw new IllegalStateException();
    }
    content.computeIfPresent(t, (k, i) -> (i == v) ? null : i - v);
    return this;
  }

  public Bank<T> addAll(Bank<T> other) {
    Objects.requireNonNull(other);
    other.content.forEach(this::add);
    return this;
  }

  public Bank<T> removeAll(Bank<T> other) {
    Objects.requireNonNull(other);
    if (other.content.entrySet().stream().anyMatch(e -> other.get(e.getKey()) > e.getValue())) {
      throw new IllegalStateException();
    }
    other.content.forEach(this::remove);
    return this;
  }

  public Map<T, Integer> content() {
    return Map.copyOf(content);
  }

  public int size() {
    return content.values().stream().mapToInt(i -> i).sum();
  }

  @Override
  public String toString() {
    return content.toString();
  }

  public boolean containsOne(T t) {
    Objects.requireNonNull(t);
    return contains(t, 1);
  }

  public boolean contains(T t, int count) {
    Objects.requireNonNull(t);
    if (count < 0) {
      throw new IllegalArgumentException("cannot contain " + count + " number of item");
    }
    return get(t) >= count;
  }

  public boolean containsAll(Bank<T> other) {
    Objects.requireNonNull(other);
    return other.content.entrySet().stream().allMatch(e -> contains(e.getKey(), e.getValue()));
  }

}
