package net.minecraftforge.caps;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

public interface ICapabilityProvider {
    default <E extends IAspect<?>> Set<E> customAspects() {
        return Collections.emptySet();
    }

    default <T, U extends IAspect<?>> OptionalCapabilityInstance<T, U> getCapability(final Capability<T> cap, final U aspect) {
        return OptionalCapabilityInstance.of(cap, aspect, instanceProvider(cap, aspect));
    }

    default <T> OptionalCapabilityInstance<T, IAspect.None> getCapability(final Capability<T> cap) {
        return OptionalCapabilityInstance.of(cap, IAspect.None.NONE, instanceProvider(cap, IAspect.None.NONE));
    }

    <T, U extends IAspect<?>> Supplier<T> instanceProvider(final Capability<T> cap, final U aspect);
    <T, U extends IAspect<?>> T nosupInstanceProvider(final Capability<T> cap, final U aspect);

    default <T, U extends IAspect<?>> T getDirectCapability(final Capability<T> cap, final U aspect) {
        return nosupInstanceProvider(cap, aspect);
    }
}
