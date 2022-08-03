package uk.antiperson.worldgen.biome.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.biome.generation.Attribute;
import uk.antiperson.worldgen.biome.generation.Attributes;

@Attribute(attribute = Attributes.HUMIDITY, minValue = 0, maxValue = 1)
public class DesertBiome extends WorldBiome {

    public DesertBiome(long seed) {
        super(seed);
    }

    @Override
    public void init() {
        addGenerator(32, 50, 15, 10, 0.1);
        addGenerator(38, 50, 10, 15, 0.05);
        addGenerator(64, 50, 5, 15, 0.01);
    }

    @Override
    public void generateBiomeTerrain(CustomChunkData data, int x, int y, int z) {
        data.setMaterial(x, y, z, Material.SAND);
        data.setLots(x, y - 3, y - 1, z, Material.SANDSTONE);
    }

    @Override
    public Biome getBukkitBiome() {
        return Biome.DESERT;
    }
}
