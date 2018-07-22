package net.minecraftforge.caps;

import java.util.function.Supplier;

public class DemoCapProvider implements ICapabilityProvider {
    @Override
    public <T, U extends IAspect<?>> Supplier<T> instanceProvider(final Capability<T> cap, final U aspect) {
        if (cap == DemoCapability.POWERCAP) {
            return ()-> cap.newInstance(() -> new DemoCapability.PowerCap.PowerData((DemoCapability.FacingAspect)aspect, 10000));
        } else if (cap == DemoCapability.NOASPECTCAP) {
            return () -> cap.newInstance(MyFishHandler::new);
        } else if (cap == BadCapHolder.BADCAP) {
            return () -> cap.newInstance(()->null);
        }
        return null;
    }

    @Override
    public <T, U extends IAspect<?>> T nosupInstanceProvider(final Capability<T> cap, final U aspect) {
        if (cap == DemoCapability.POWERCAP) {
            return cap.newInstance(() -> new DemoCapability.PowerCap.PowerData((DemoCapability.FacingAspect)aspect, 10000));
        } else if (cap == DemoCapability.NOASPECTCAP) {
            return cap.newInstance(MyFishHandler::new);
        }
        return null;
    }


    public static class MyFishHandler implements DemoCapability.NoAspectCap.ITestFishHandler {
        @Override
        public boolean isReal() {
            return false;
        }
    }
}
