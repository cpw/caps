package net.minecraftforge.caps;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalCapabilityInstance<T> {
    private final Supplier<T> supplier;
    private AtomicReference<T> resolved;

    private static final OptionalCapabilityInstance<Void> EMPTY = new OptionalCapabilityInstance<>(null);

    @SuppressWarnings("unchecked")
    private static <T> OptionalCapabilityInstance<T> empty() {
        return (OptionalCapabilityInstance<T>)EMPTY;
    }

    private OptionalCapabilityInstance(Supplier<T> instanceSupplier)
    {
        this.supplier = instanceSupplier;
    }

    public static <T> OptionalCapabilityInstance<T> of(final Supplier<T> instanceSupplier) {
        return new OptionalCapabilityInstance<>(instanceSupplier);
    }

    private T getValue()
    {
        if (resolved != null) {
            return resolved.get();
        }
        if (supplier != null) {
            resolved = new AtomicReference<>(null);
            try {
                resolved.set(supplier.get());
                return resolved.get();
            } catch (Throwable e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Return {@code true} if there is a mod object present, otherwise {@code false}.
     *
     * @return {@code true} if there is a mod object present, otherwise {@code false}
     */
    public boolean isPresent() {
        return getValue() != null;
    }

    /**
     * If a mod object is present, invoke the specified consumer with the object,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a mod object is present
     * @throws NullPointerException if mod object is present and {@code consumer} is
     * null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (getValue() != null)
            consumer.accept(getValue());
    }

    /**
     * If a mod object is present, and the mod object matches the given predicate,
     * return an {@code OptionalMod} describing the value, otherwise return an
     * empty {@code OptionalMod}.
     *
     * @param predicate a predicate to apply to the mod object, if present
     * @return an {@code OptionalMod} describing the value of this {@code OptionalMod}
     * if a mod object is present and the mod object matches the given predicate,
     * otherwise an empty {@code OptionalMod}
     * @throws NullPointerException if the predicate is null
     */
    public OptionalCapabilityInstance<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return OptionalCapabilityInstance.of(()->predicate.test(getValue()) ? getValue() : null);
    }

    /**
     * If a mod object is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @apiNote This method supports post-processing on optional values, without
     * the need to explicitly check for a return status.
     *
     * @param <U> The type of the result of the mapping function
     * @param mapper a mapping function to apply to the mod object, if present
     * @return an {@code Optional} describing the result of applying a mapping
     * function to the mod object of this {@code OptionalMod}, if a mod object is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null
     */
    public<U> OptionalCapabilityInstance<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return OptionalCapabilityInstance.of(()->isPresent() ? mapper.apply(getValue()) : null);
    }

    /**
     * If a value is present, apply the provided {@code Optional}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optional}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optional},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optional}.
     *
     * @param <U> The type parameter to the {@code Optional} returned by
     * @param mapper a mapping function to apply to the mod object, if present
     *           the mapping function
     * @return the result of applying an {@code Optional}-bearing mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}
     * @throws NullPointerException if the mapping function is null or returns
     * a null result
     */
    public<U> OptionalCapabilityInstance<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Objects.requireNonNull(mapper);
        return OptionalCapabilityInstance.of(()-> isPresent() ? mapper.apply(getValue()).orElse(null) : null);
    }

    /**
     * Return the mod object if present, otherwise return {@code other}.
     *
     * @param other the mod object to be returned if there is no mod object present, may
     * be null
     * @return the mod object, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return getValue() != null ? getValue() : other;
    }

    /**
     * Return the mod object if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no mod object
     * is present
     * @return the mod object if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if mod object is not present and {@code other} is
     * null
     */
    public T orElseGet(Supplier<? extends T> other) {
        return getValue() != null ? getValue() : other.get();
    }

    /**
     * Return the contained mod object, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present mod object
     * @throws X if there is no mod object present
     * @throws NullPointerException if no mod object is present and
     * {@code exceptionSupplier} is null
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (getValue() != null) {
            return getValue();
        } else {
            throw exceptionSupplier.get();
        }
    }
}
