package uk.antiperson.worldgen.biome.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.biome.generation.Attribute;
import uk.antiperson.worldgen.biome.generation.Attributes;
import uk.antiperson.worldgen.noise.NoiseGenerator;

@Attribute(attribute = Attributes.HUMIDITY, minValue = 5, maxValue = 7)
public class DryBiome extends WorldBiome {

    public DryBiome(long seed) {
        super(seed);
    }

    @Override
    public void init() {
        addGenerator(32, 55, 15, 10, 0.1);
        addGenerator(38, 55, 10, 15, 0.05);
        addGenerator(64, 55, 5, 15, 0.01);
    }

    @Override
    public void generateBiomeTerrain(CustomChunkData data, int x, int y, int z) {
        Material material = Material.DIRT;
        data.setMaterial(x, y, z, material);
        data.setLots(x, y - 3, y - 1, z, Material.DIRT);
    }

    @Override
    public Biome getBukkitBiome() {
        return Biome.SAVANNA;
    }
}
