package uk.antiperson.worldgen.biome.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.biome.generation.Attribute;
import uk.antiperson.worldgen.biome.generation.Attributes;

@Attribute(attribute = Attributes.HUMIDITY, minValue = 7, maxValue = 10)
public class HillBiome extends WorldBiome {

    public HillBiome(long seed) {
        super(seed);
    }

    @Override
    public void init() {
        addGenerator(16, 50, 50, 2, 0.5);
        addGenerator(16, 50, 40, 5, 0.1);
        addGenerator(32, 50, 35, 6, 0.1);
        addGenerator(32, 50, 30, 8, 0.01);
        addGenerator(64, 50, 20, 16, 0.01);
        addGenerator(64, 50, 15, 32, 0.005);
    }

    @Override
    public void generateBiomeTerrain(CustomChunkData data, int x, int y, int z) {
        data.setMaterial(x, y, z, Material.GRASS_BLOCK);
        data.setLots(x, y - 3, y - 1, z, Material.DIRT);
    }

    @Override
    public Biome getBukkitBiome() {
        return Biome.TAIGA;
    }
}
