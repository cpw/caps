package net.minecraftforge.caps;

import java.util.function.Supplier;

public class BadCap {
    static {
        if (1==1) {
            throw new RuntimeException("BARF!");
        }
    }
    public BadCap(Supplier<?> thing) {

    }

    public BadCap getSubCap(String name) {
        return new BadCap(()->null);
    }

    public int getNumber() {
        return 4;
    }
}
