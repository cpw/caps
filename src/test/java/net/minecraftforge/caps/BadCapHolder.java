package net.minecraftforge.caps;

public class BadCapHolder {
    public static Capability<BadCap> BADCAP = new Capability<>("badcap", BadCap::new);
}
