package uk.antiperson.worldgen.biome;

import uk.antiperson.worldgen.CustomChunkData;

public interface Biome {

    int generateHeight(int x, int y);

    void init();

    BiomeType getBiomeType();

    void generateBiomeTerrain(CustomChunkData data, int x, int y, int z);

    void generateTerrain(CustomChunkData data, int x, int y, int z);

    long getSeed();

    org.bukkit.block.Biome getBukkitBiome();

}
