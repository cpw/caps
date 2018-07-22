package net.minecraftforge.caps;

public final class CapabilityRef<T> {
    private final Capability<T> cap;

    private CapabilityRef(final Capability<T> cap) {
        this.cap = cap;
    }

    public <U> CapabilityRef<U> of(final String name) {
        return new CapabilityRef<>(CapabilityManager.find(name));
    }

    boolean isPresent() {
        return cap != null;
    }

    Capability<T> getCap() {
        return this.cap;
    }
}
