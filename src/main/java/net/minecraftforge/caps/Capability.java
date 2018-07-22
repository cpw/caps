package net.minecraftforge.caps;

import java.util.function.Function;
import java.util.function.Supplier;

public class Capability<T> {
    private final String name;
    private final Function<Supplier<?>, T> constructor;

    Capability(final String name, Function<Supplier<?>, T> constructor) {
        this.name = name;
        this.constructor = constructor;
    }

    T newInstance(final Supplier<?> data) {
        return constructor.apply(data);
    }
}
