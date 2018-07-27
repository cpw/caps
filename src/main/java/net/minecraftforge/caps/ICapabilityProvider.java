package net.minecraftforge.caps;

import javax.annotation.Nullable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.EnumFacing;

public interface ICapabilityProvider {
    //This is here strictly to shut up lazy people who complain about sending in null. Sadly default methods cant be final
    default <T> OptionalCapabilityInstance<T> getCapability(final Capability<T> cap) {
        return getCapability(cap, null);
    }
    <T> OptionalCapabilityInstance<T> getCapability(final Capability<T> cap, final @Nullable EnumFacing side);
}
