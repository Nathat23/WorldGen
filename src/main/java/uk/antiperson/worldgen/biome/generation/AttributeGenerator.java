package uk.antiperson.worldgen.biome.generation;

import uk.antiperson.worldgen.noise.NoiseGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class AttributeGenerator {

    private NoiseGenerator noiseGenerator;

    public AttributeGenerator(int octaves, double frequency, double amplitude) {
        noiseGenerator = new NoiseGenerator(ThreadLocalRandom.current().nextLong(), octaves, frequency, amplitude);
    }

    public int getValue(int x, int z) {
        return (int) Math.ceil((noiseGenerator.generateNoise(x, z) + 1) * 5);
    }
}
