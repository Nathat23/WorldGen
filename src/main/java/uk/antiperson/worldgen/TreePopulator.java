package uk.antiperson.worldgen;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;
import uk.antiperson.worldgen.biome.BiomeType;
import uk.antiperson.worldgen.biome.biomes.HillBiome;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TreePopulator extends BlockPopulator {

    private CustomWorldGen customWorldGen;
    List<StructureLayer> structureLayers;

    public TreePopulator(CustomWorldGen customWorldGen) {
        this.customWorldGen = customWorldGen;
        structureLayers = loadHouse();
    }

    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk source) {
        CustomChunk customChunk = customWorldGen.getCustomChunk(source);
        for (SimpleBlock block : customChunk.getPopulatedBlock()) {
            block.toBukkit().setType(block.getMaterial());
        }
        customChunk.clearPopulatedBlocks();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(4, 6); i++) {
            int randX = ThreadLocalRandom.current().nextInt(0, 15);
            int randZ = ThreadLocalRandom.current().nextInt(0, 15);
            if (!(customChunk.getBiomeMap().getBiomeType(randX, randZ).getBiomeType() == BiomeType.HILL)) {
                continue;
            }
            int maxY = customChunk.getHeightMap().getHeightAt(randX, randZ);
            /*for (StructureLayer structureLayer : structureLayers) {
                int maxX = Math.min(structureLayer.getXLength() + randX, 15 - randX);
                int maxZ = Math.min(structureLayer.getZLength() + randZ, 15 - randZ);
                int highest = customChunk.getHeightMap().getHighestInRegion(randX, randZ, maxX, maxZ);
                if (highest > maxY) {
                    maxY = highest;
                }
            }*/
            HashMap<Location, Material> blocks = new HashMap<>();
            for (StructureLayer structureLayer : structureLayers) {
                int maxX = randX + structureLayer.getXLength();
                int maxZ = randZ + structureLayer.getZLength();
                int matId = 0;
                for (int x = randX; x < maxX; x++) {
                    for (int z = randZ; z < maxZ; z++) {
                        Material material = structureLayer.getMaterial(matId);
                        if (material.isAir()) {
                            matId += 1;
                            continue;
                        }
                        int blockX = (source.getX() * 16) + x;
                        int blockZ = (source.getZ() * 16) + z;
                        Location location = new Location(world, blockX, maxY, blockZ);
                        blocks.put(location, material);
                        matId += 1;
                    }
                }
                maxY += 1;
            }
            for (Map.Entry<Location, Material> entry : blocks.entrySet()) {
                Location location = entry.getKey();
                Material material = entry.getValue();
                CustomChunk chunk = customWorldGen.getCustomChunk(location.getChunk());
                if (chunk.isTerrainGenerated()) {
                    location.getBlock().setType(material);
                    continue;
                }
                chunk.addPopulatedBlock(new SimpleBlock(location, material));
            }
        }
    }

    private List<StructureLayer> loadHouse() {
        InputStreamReader reader = new InputStreamReader(customWorldGen.wg.getResource("platform.yml"));
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(reader);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<StructureLayer> materials = new ArrayList<>();
        for (String key : fileConfiguration.getKeys(false)) {
            int xLength = fileConfiguration.getInt(key + ".size-x");
            int zLength = fileConfiguration.getInt(key + ".size-z");
            StructureLayer structureLayer = new StructureLayer(xLength, zLength, Integer.parseInt(key.replace("layer-", "")));
            for (String stringMat : fileConfiguration.getStringList(key + ".mats")) {
                Material material = Material.matchMaterial(stringMat);
                structureLayer.addMaterial(material);
            }
            materials.add(structureLayer);
        }
        return materials;
    }

}
