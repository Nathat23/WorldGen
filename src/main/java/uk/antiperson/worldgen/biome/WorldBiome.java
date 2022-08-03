package uk.antiperson.worldgen.biome;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.noise.HeightGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class WorldBiome implements Biome {

    private final long biomeSeed;
    private final Set<HeightGenerator> generatorSet;
    private BiomeType biomeType;

    public WorldBiome(long seed) {
        this.biomeSeed = seed;
        this.generatorSet = new HashSet<>();
    }

    @Override
    public long getSeed() {
        return biomeSeed;
    }

    @Override
    public int generateHeight(int x, int y) {
        int height = 0;
        for (HeightGenerator heightGenerator : getGeneratorSet()) {
            int genHeight = heightGenerator.generateHeight(x,y);
            height = Math.max(height, genHeight);
        }
        return height;
    }

    public void addGenerator(int octaves, int baseHeight, double multiplier, double frequency, double amplitude) {
        generatorSet.add(new HeightGenerator(createGenerator(octaves, 0.005), baseHeight, multiplier, frequency, amplitude));
    }

    public Set<HeightGenerator> getGeneratorSet() {
        return generatorSet;
    }

    public SimplexOctaveGenerator createGenerator(int octaves, double scale) {
        SimplexOctaveGenerator octaveGenerator = new SimplexOctaveGenerator(getSeed(), octaves);
        octaveGenerator.setScale(scale);
        return octaveGenerator;
    }

    @Override
    public void generateTerrain(CustomChunkData data, int x, int y, int z) {
        data.setMaterial(x,0, z, Material.BEDROCK);
        data.setLots(x,1, y, z, Material.STONE);
        if (y < 55) {
            data.setLots(x, y, 55, z, Material.WATER);
        }
    }

    @Override
    public BiomeType getBiomeType() {
        if (biomeType != null) {
            return biomeType;
        }
        for (BiomeType biomeType : BiomeType.values()) {
            if (getClass().isAssignableFrom(biomeType.getBiomeClass())) {
                this.biomeType = biomeType;
                return biomeType;
            }
        }
        throw new RuntimeException("Could not determine biome type for " + getClass().getName());
    }

}
