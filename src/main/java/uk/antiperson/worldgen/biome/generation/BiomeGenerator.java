package uk.antiperson.worldgen.biome.generation;

import uk.antiperson.worldgen.biome.BiomeType;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.biome.biomes.DesertBiome;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BiomeGenerator {

    private Set<BiomeAttribute> attributeSet;

    public BiomeGenerator() {
        attributeSet = new HashSet<>(Attributes.values().length);
    }

    public void init() {
        AttributeGenerator generator = new AttributeGenerator(2, 0.5, 3);
        BiomeAttribute dryness = new BiomeAttribute(Attributes.HUMIDITY, generator);
        AttributeGenerator generator1 = new AttributeGenerator(1, 0.5, 5);
        BiomeAttribute temperature = new BiomeAttribute(Attributes.TEMPERATURE, generator1);
        attributeSet.add(dryness);
        attributeSet.add(temperature);
        for (BiomeType biomeType : BiomeType.values()) {
            Annotation[] annotations = biomeType.getBiomeClass().getAnnotations();
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof Attribute attribute)) {
                    continue;
                }
                if (attribute.maxValue() < attribute.minValue() || attribute.minValue() < 0 || attribute.maxValue() > 10) {
                    throw new UnsupportedOperationException("Attribute " + attribute.attribute().toString() + " out of range for " + biomeType.toString());
                }
            }
        }
    }

    public BiomeType getBiome(int x, int z) {
        a: for (BiomeType biomeType : BiomeType.values()) {
            Annotation[] annotations = biomeType.getBiomeClass().getAnnotations();
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof MultipleAttribute multipleAttribute)) {
                    continue;
                }
                for (Attribute attribute : multipleAttribute.value()) {
                    for (BiomeAttribute biomeAttribute : attributeSet) {
                        if (biomeAttribute.getAttributes() != attribute.attribute()) {
                            continue;
                        }
                        int value = biomeAttribute.getGenerator().getValue(x, z);
                        if (value < attribute.minValue() || value > attribute.maxValue()) {
                            continue a;
                        }
                        break;
                    }
                }
            }
            return biomeType;
        }
        return null;
    }

}
