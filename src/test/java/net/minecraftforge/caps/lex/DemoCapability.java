package net.minecraftforge.caps.lex;

import net.minecraftforge.caps.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.EnumFacing;
import net.minecraftforge.common.capabilities.NBTBase;

public class DemoCapability {
    public static interface IEnergyCap {
        int get();
        void set(int v);
    }

    public static class Default implements IEnergyCap {
        private int value;
        public int get() { return value; }
        public void set(int v) { value = v; }
    }

    public static class Storage implements Capability.IStorage<IEnergyCap> {
        public NBTBase writeNBT(Capability<IEnergyCap> capability, IEnergyCap instance, EnumFacing side) { return null; }
        public void readNBT(Capability<IEnergyCap> capability, IEnergyCap instance, EnumFacing side, NBTBase nbt) {}

    }

    public static Capability<IEnergyCap> ENERGY_CAP = CapabilityManager.register(IEnergyCap.class, new Storage(), Default::new);

}
