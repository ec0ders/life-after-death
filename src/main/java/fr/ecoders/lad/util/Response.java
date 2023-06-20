package fr.ecoders.lad.util;

import java.util.Objects;
import java.util.function.Consumer;

public final class Response<V, E> {
    private final V v;
    private final E e;

    private Response(V v, E e) {
        this.v = v;
        this.e = e;
    }

    public static <V, E> Response<V, E> ofValue(V v) {
        return new Response<>(v, null);
    }

    public static <V, E> Response<V, E> ofError(E e) {
        return new Response<>(null, e);
    }

    public void handle(Consumer<V> valueConsumer, Consumer<E> errorConsumer) {
        if (v != null) {
            valueConsumer.accept(v);
            return;
        }
        errorConsumer.accept(e);
    }

    public V v() {
        return v;
    }

    public E e() {
        return e;
    }

    @Override
    public String toString() {
        return "Response[" +
                "v=" + v + ", " +
                "e=" + e + ']';
    }

}
