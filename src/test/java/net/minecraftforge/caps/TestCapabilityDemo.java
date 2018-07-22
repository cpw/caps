package net.minecraftforge.caps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCapabilityDemo {
    @Test
    public void testCapability() {
        DemoCapProvider democap = new DemoCapProvider();

        democap.getCapability(DemoCapability.NOASPECTCAP, null).ifPresent(DemoCapability.NoAspectCap::isReal);
        democap.getCapability(DemoCapability.NOASPECTCAP).ifPresent(DemoCapability.NoAspectCap::isReal);

        int power = democap.getCapability(DemoCapability.POWERCAP, DemoCapability.FacingAspect.UP).
                filter(c->c.getType() == DemoCapability.PowerCap.PowerType.STORE).
                map(DemoCapability.PowerCap::getStored).
                orElse(0);
        assertEquals(10000,power);

    }

    @Test
    public void testBadCap() {
        DemoCapProvider democap = new DemoCapProvider();
        int val = democap.getCapability(BadCapHolder.BADCAP).
                filter(c->c.getNumber() == 4).
                map(c->c.getSubCap("CHEESE")).
                map(BadCap::getNumber).
                orElse(5);
        assertEquals(5, val);
    }
}
