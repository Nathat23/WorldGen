package uk.antiperson.worldgen.biome.generation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleAttribute {

    Attribute[] value();

}
