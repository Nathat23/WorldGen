package uk.antiperson.worldgen.noise;

import org.bukkit.util.noise.SimplexOctaveGenerator;

public class NoiseGenerator {

    private final double frequency;
    private final double amplitude;
    private final SimplexOctaveGenerator octaveGenerator;

    public NoiseGenerator(SimplexOctaveGenerator octaveGenerator, double frequency, double amplitude) {
        octaveGenerator.setScale(0.005);
        this.octaveGenerator = octaveGenerator;
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    public NoiseGenerator(long seed, int octaves, double frequency, double amplitude) {
        this(new SimplexOctaveGenerator(seed, octaves), frequency, amplitude);
    }

    public double generateNoise(int x, int z) {
        return octaveGenerator.noise(x, z, frequency, amplitude, true);
    }
}
