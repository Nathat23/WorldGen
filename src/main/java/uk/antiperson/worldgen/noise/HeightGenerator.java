package uk.antiperson.worldgen.noise;

import org.bukkit.util.noise.SimplexOctaveGenerator;

public class HeightGenerator extends NoiseGenerator {

    private final int baseHeight;
    private final double multiplier;

    public HeightGenerator(SimplexOctaveGenerator octaveGenerator, int baseHeight, double multiplier, double frequency, double amplitude) {
        super(octaveGenerator, frequency, amplitude);
        this.baseHeight = baseHeight;
        this.multiplier = multiplier;
    }

    public int generateHeight(int x, int z) {
        return (int) (generateNoise(x, z) * multiplier) + baseHeight;
    }

}
