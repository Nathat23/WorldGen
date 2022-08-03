package uk.antiperson.worldgen.carver;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.Vector;
import uk.antiperson.worldgen.CoordinatePair;
import uk.antiperson.worldgen.CustomChunk;
import uk.antiperson.worldgen.CustomChunkData;
import uk.antiperson.worldgen.CustomWorldGen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Carver {

    private final CarverCache carverCache;
    private final CustomWorldGen customWorldGen;

    public Carver(CustomWorldGen customWorldGen) {
        this.customWorldGen = customWorldGen;
        this.carverCache = new CarverCache(this);
    }

    public void carve(CustomChunkData customChunkData, CustomChunk customChunk) {
        List<CoordinatePair> coordinatePairSet = carverCache.getPoints(customChunk.getX(), customChunk.getZ());
        if (coordinatePairSet == null) {
            return;
        }
        for (CoordinatePair coordinatePair : carverCache.getPoints(customChunk.getX(), customChunk.getZ())) {
            System.out.println("setting: " + coordinatePair.getX() + "," + coordinatePair.getZ());
            for (int i = 0; i < 2; i++) {
                customChunkData.setMaterial(i, 100, i, Material.DIAMOND_BLOCK);
            }
        }
    }
}
