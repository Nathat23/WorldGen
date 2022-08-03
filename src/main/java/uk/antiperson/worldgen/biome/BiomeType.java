package uk.antiperson.worldgen.biome;

import uk.antiperson.worldgen.biome.biomes.DesertBiome;
import uk.antiperson.worldgen.biome.biomes.DesertHillsBiome;
import uk.antiperson.worldgen.biome.biomes.HillBiome;
import uk.antiperson.worldgen.biome.biomes.DryBiome;

public enum BiomeType {
    HILL(HillBiome.class),
    DRY(DryBiome.class),
    DESERT_HILLS(DesertHillsBiome.class),
    DESERT(DesertBiome.class);

    private Class<? extends WorldBiome> biomeClass;
    BiomeType(Class<? extends WorldBiome> biomeClass) {
        this.biomeClass = biomeClass;
    }

    public Class<? extends WorldBiome> getBiomeClass() {
        return biomeClass;
    }
}
