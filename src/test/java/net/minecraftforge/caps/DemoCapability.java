package net.minecraftforge.caps;

import java.util.function.Supplier;

public class DemoCapability {
    public enum FacingAspect implements IAspect<FacingAspect> {
        UP, DOWN, EAST, WEST, NORTH, SOUTH;

        @Override
        public FacingAspect value() {
            return this;
        }
    }

    public static class PowerCap {
        private final PowerData handler;
        enum PowerType {STORE, FISH}
        public PowerCap(Supplier<?> powerHolder) {
            this.handler = (PowerData) powerHolder.get();
        }

        public PowerType getType() {
            return PowerType.STORE;
        }

        public int getStored() {
            return handler.getStoredPower();
        }

        public static class PowerData {
            private int storedPower;

            public PowerData(final FacingAspect aspect, final int initialPower) {
                this.storedPower = initialPower;
            }

            public int getStoredPower() {
                return storedPower;
            }
        }
    }

    public static class NoAspectCap {
        private final ITestFishHandler handler;

        public NoAspectCap(Supplier<?> noAspectHolder) {
            handler = (ITestFishHandler) noAspectHolder.get();
        }

        public boolean isReal() {
            return handler.isReal();
        }

        public interface ITestFishHandler {
            boolean isReal();
        }
    }

    public static Capability<PowerCap> POWERCAP = new Capability<>("demopower", PowerCap::new);
    public static Capability<NoAspectCap> NOASPECTCAP = new Capability<>("demonoaspect", NoAspectCap::new);
}
