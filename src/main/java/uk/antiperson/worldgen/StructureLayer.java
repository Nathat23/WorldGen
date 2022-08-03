package uk.antiperson.worldgen;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class StructureLayer {

    private final List<Material> materials;
    private final int x;
    private final int z;
    private final int layer;
    private final int maxBlocks;

    public StructureLayer(int xLength, int zLength, int layer) {
        this.x = xLength;
        this.z = zLength;
        this.layer = layer;
        this.maxBlocks = xLength * zLength;
        this.materials = new ArrayList<>(maxBlocks);
    }

    public void addMaterial(Material material) {
        if (materials.size() > getMaxBlocks()) {
            throw new RuntimeException("More blocks added to structure layer list than is allowed!");
        }
        materials.add(material);
    }

    public int getXLength() {
        return x;
    }

    public int getZLength() {
        return z;
    }

    public int getLayer() {
        return layer;
    }

    public int getMaxBlocks() {
        return maxBlocks;
    }

    public Material getMaterial(int id) {
        return materials.get(id);
    }
}
