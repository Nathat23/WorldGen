package uk.antiperson.worldgen.biome.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.biome.generation.Attribute;
import uk.antiperson.worldgen.biome.generation.Attributes;

@Attribute(attribute = Attributes.HUMIDITY, minValue = 2, maxValue = 4)
public class DesertHillsBiome extends HillBiome {

    public DesertHillsBiome(long seed) {
        super(seed);
    }

    @Override
    public void generateBiomeTerrain(CustomChunkData data, int x, int y, int z) {
        data.setMaterial(x, y, z, Material.BASALT);
        data.setLots(x, y - 3, y - 1, z, Material.SANDSTONE);
    }

    @Override
    public Biome getBukkitBiome() {
        return Biome.DESERT;
    }
}
