package uk.antiperson.worldgen.biome.generation;

import java.util.concurrent.ThreadLocalRandom;

public class BiomeAttribute {

    private Attributes attributes;
    private AttributeGenerator attributeGenerator;

    public BiomeAttribute(Attributes attributes, AttributeGenerator generator) {
        this.attributes = attributes;
        this.attributeGenerator = generator;
    }

    public AttributeGenerator getGenerator() {
        return attributeGenerator;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}
