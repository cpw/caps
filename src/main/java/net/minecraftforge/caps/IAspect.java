package net.minecraftforge.caps;


public interface IAspect<T extends Enum<T>> {
    enum None implements IAspect<None> {
        NONE() {
            public None value() { return this; }
        }
    }
    T value();
}
