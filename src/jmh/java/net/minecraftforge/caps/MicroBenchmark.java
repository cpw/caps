package net.minecraftforge.caps;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MicroBenchmark {
    @State(Scope.Benchmark)
    public static class CapHolder {
        public DemoCapProvider provider = new DemoCapProvider();
        public Object fishHandler = new DemoCapProvider.MyFishHandler();
    }


    @Benchmark
    public void testEmptyMethod() {
        // This method is completely empty
    }

    @Benchmark
    public Boolean testNiceAPI(CapHolder holder) {
        return holder.provider.getCapability(DemoCapability.NOASPECTCAP, null).map(DemoCapability.NoAspectCap::isReal).orElse(Boolean.FALSE);
    }

    @Benchmark
    public Boolean testNastyAPI(CapHolder holder) {
        DemoCapability.NoAspectCap t = holder.provider.getDirectCapability(DemoCapability.NOASPECTCAP, null);
        return t != null ? t.isReal() : Boolean.FALSE;
    }

    @Benchmark
    public Boolean testOldHardAPI(CapHolder holder) {
        return (holder.fishHandler instanceof DemoCapability.NoAspectCap.ITestFishHandler) ?
                ((DemoCapability.NoAspectCap.ITestFishHandler)holder.fishHandler).isReal() : Boolean.FALSE;
    }
}
