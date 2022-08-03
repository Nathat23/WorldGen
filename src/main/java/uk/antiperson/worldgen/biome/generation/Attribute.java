package uk.antiperson.worldgen.biome.generation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(MultipleAttribute.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    Attributes attribute();
    int minValue();
    int maxValue();
}
