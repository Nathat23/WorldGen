package uk.antiperson.worldgen.biome;

import uk.antiperson.worldgen.biome.generation.BiomeGenerator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class BiomeManager {

    private final BiomeGenerator biomeGenerator;
    private final Set<WorldBiome> biomes;
    private long seed;

    public BiomeManager(long seed) {
        this.seed = seed;
        this.biomes = new HashSet<>();
        this.biomeGenerator = new BiomeGenerator();
    }

    public void init() {
        for (BiomeType type : BiomeType.values()) {
            WorldBiome worldBiome = createInstance(type);
            worldBiome.init();
            biomes.add(worldBiome);
        }
        biomeGenerator.init();
    }

    public WorldBiome createInstance(BiomeType type) {
        try {
            return type.getBiomeClass().getConstructor(long.class).newInstance(seed);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public WorldBiome getBiome(int x, int z) {
        BiomeType biomeType = getBiomeType(x, z);
        for (WorldBiome worldBiome : biomes) {
            if (worldBiome.getBiomeType() == biomeType) {
                return worldBiome;
            }
        }
        return null;
    }

    private BiomeType getBiomeType(int x, int z) {
        return biomeGenerator.getBiome(x, z);
    }


}
