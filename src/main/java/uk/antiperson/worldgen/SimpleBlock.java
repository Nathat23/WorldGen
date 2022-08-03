package uk.antiperson.worldgen;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SimpleBlock {

    private final Location location;
    private final Material material;

    public SimpleBlock(Location location, Material material) {
        this.location = location;
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return material;
    }

    public Block toBukkit() {
        return getLocation().getBlock();
    }
}
