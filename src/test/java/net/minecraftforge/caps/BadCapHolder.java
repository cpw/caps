package net.minecraftforge.caps;

import net.minecraftforge.common.capabilities.Capability;

public class BadCapHolder {
    public static Capability<BadCap> BADCAP = new Capability<>("badcap", BadCap::new);
}
