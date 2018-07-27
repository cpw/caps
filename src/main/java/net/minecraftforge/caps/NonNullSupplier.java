package net.minecraftforge.caps;

import javax.annotation.Nonnull;

//Exactly like Supplier, except there IS a contract that each invocation should return a new unique instance and it must not be null.

@FunctionalInterface
public interface NonNullSupplier<T> {
    @Nonnull T get();
}
