package net.minecraftforge.caps;

import com.google.gson.reflect.TypeToken;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityManager {
    public static <T> Capability<T> find(final TypeToken<T> type) { //Cant use strings to look things up because everything is registered by interface class name.
        return null;                                               //Maybe make this a nonnull return, and have it update when the thing is registered? Perhaps with a listener list? So we can move away from annotations for population/events?
    }
    public static <T> Capability<T> register(Class<T> c, IStorage<T> storage, NonNullSupplier<T> factory){
        return null;
    }
}
