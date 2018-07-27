package net.minecraftforge.caps.lex;

import com.google.gson.reflect.TypeToken;

import net.minecraftforge.caps.CapabilityManager;
import net.minecraftforge.caps.ICapabilityProvider;
import net.minecraftforge.caps.OptionalCapabilityInstance;
import net.minecraftforge.caps.lex.DemoCapability.IEnergyCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.EnumFacing;

public class DemoTileEntity implements ICapabilityProvider {
    private static Capability<IEnergyCap> ENERGY = CapabilityManager.find(new TypeToken<IEnergyCap>() {}); //Either do it this way, to get rid of the annotation magic.
    @CapabilityInject(IEnergyCap.class)
    private static Capability<IEnergyCap> ENERGY2 = null; //Or we can keep this to keep the old way, dont really care.

    //Instance variables! We really need to have a shorter name, CapabilityRef?
    private OptionalCapabilityInstance<IEnergyCap> myEnergyInput = OptionalCapabilityInstance.of(() -> new Mutable());
    private OptionalCapabilityInstance<IEnergyCap> myEnergyOutput = OptionalCapabilityInstance.of(() -> new Immutable(1000));
    int time = 0;


    private class Mutable implements IEnergyCap {
        private int value;
        public int get() { return value; }
        public void set(int v) { this.value = v; }
    }
    private class Immutable implements IEnergyCap {
        private final int value;
        Immutable(int v){ this.value = v; }
        public int get() { return value; }
        public void set(int v) {}
    }

    @Override
    public <T> OptionalCapabilityInstance<T> getCapability(Capability<T> cap, EnumFacing side) {
        if (cap == null /*cap.isPresent() ?*/) return OptionalCapabilityInstance.empty();

        if (cap == ENERGY) {
            if (side == EnumFacing.UP)
                return myEnergyInput.cast();
            else if (side == EnumFacing.DOWN)
                return myEnergyOutput.cast();
        }

        return null;
    }

    public void tick() {
        if (ENERGY2 != null/*ENERGY.isPresent()?*/) { //If IEnergy is available I can play with this!
            if (time++ == 100) {
                myEnergyOutput.invalidate();
                myEnergyOutput = OptionalCapabilityInstance.of(null); //I loose the ability to output every 100 ticks, for 100 ticks. I Don't use empty() so I can notify people when I change!
            } else if (time++ == 200) {
                time = 0;
                myEnergyOutput.invalidate(); //Old way would be world.notifyNeighbors
                myEnergyOutput = OptionalCapabilityInstance.of(() -> new Immutable(1000));
            }
        }
    }
}
