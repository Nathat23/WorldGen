package uk.antiperson.worldgen;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.jetbrains.annotations.NotNull;
import uk.antiperson.worldgen.biome.BiomeManager;
import uk.antiperson.worldgen.biome.BiomeType;
import uk.antiperson.worldgen.biome.WorldBiome;
import uk.antiperson.worldgen.carver.Carver;
import uk.antiperson.worldgen.noise.HeightGenerator;
import uk.antiperson.worldgen.noise.NoiseGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class CustomWorldGen extends ChunkGenerator {

    private int waterHeight = 55;
    private long seed = 6497432116936181725L;
    private ChunkData chunkData;
    private final Set<CustomChunk> customChunks = ConcurrentHashMap.newKeySet();
    private BiomeManager biomeManager;
    private Carver carver;
    WorldGen wg;
    public CustomWorldGen(WorldGen wg) {
        this.wg = wg;
        this.biomeManager = new BiomeManager(seed);
        biomeManager.init();
        carver = new Carver(this);
        wg.getServer().getPluginManager().registerEvents(new ChunkListener(this), wg);
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        CustomChunk customChunk = getCustomChunk(Bukkit.getWorld(worldInfo.getUID()), chunkX, chunkZ);
        // id = x, pos on array 2 = z, int = y
        ArrayList<ArrayList<Integer>> coords = new ArrayList<>();
        for (int x = -2; x < 18; x++) {
            ArrayList<Integer> ords = new ArrayList<>();
            for (int z = -2; z < 18; z++) {
                int xCoord = (chunkX * 16) + x;
                int zCoord = (chunkZ * 16) + z;
                WorldBiome worldBiome = biomeManager.getBiome(xCoord, zCoord);
                int genHeight = worldBiome.generateHeight(xCoord, zCoord);
                ords.add(genHeight);
                if (x >= 0 && x < 16 && z >= 0 && z < 16) {
                    //customChunk.getHeightMap().addHeight(genHeight);
                    /*for (int y = 0; y < 256; y++) {
                        biome.setBiome(x, y, z, worldBiome.getBukkitBiome());
                        if (genHeight < waterHeight) {
                            biome.setBiome(x,y,z,Biome.RIVER);
                        }
                    }*/
                    customChunk.getBiomeMap().addBiome(worldBiome);
                }
            }
            coords.add(ords);
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int total = 0;
                for(int cx=0; cx<5; ++cx) {
                    for (int cz = 0; cz < 5; ++cz) {
                        ArrayList<Integer> zCords = coords.get(x + cx);
                        int yCords = zCords.get(z + cz);
                        total += yCords;
                    }
                }
                total /= 25;
                customChunk.getHeightMap().addHeight(total);
            }
        }
        CustomChunkData customChunkData = new CustomChunkData(chunkData);
        int counter = 0;
        for (WorldBiome worldBiome : customChunk.getBiomeMap().getBiomes()) {
            int x = counter / 16;
            int z = counter % 16;
            int y = customChunk.getHeightMap().getHeightAt(x,z);
            worldBiome.generateTerrain(customChunkData, x, y, z);
            worldBiome.generateBiomeTerrain(customChunkData, x, y, z);
            counter++;
        }

        //carver.carve(customChunkData, customChunk);
        customChunk.setTerrainGenerated(true);
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return true;
    }

    /*@NotNull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return Arrays.asList(new TreePopulator(this));
    }*/

    public CustomChunk getCustomChunk(Chunk chunk) {
        return getCustomChunk(chunk.getWorld(), chunk.getX(), chunk.getZ());
    }

    public CustomChunk getCustomChunk(World world, int chunkX, int chunkZ) {
        for (CustomChunk customChunk : customChunks) {
            if (customChunk.getWorld().equals(world) && customChunk.getX() == chunkX && customChunk.getZ() == chunkZ) {
                return customChunk;
            }
        }
        CustomChunk customChunk = new CustomChunk(world, chunkX, chunkZ);
        customChunks.add(customChunk);
        return customChunk;
    }

    public Set<CustomChunk> getCustomChunks() {
        return customChunks;
    }

    public ArrayList<Integer> smoothing(ArrayList<Integer> heights) {
        int lowestHeight = heights.get(0);
        int lowestPos = 0;
        int highestHeight = heights.get(0);
        int highestPos = 0;
        int counter = 0;
        for (Integer height : heights) {
            if (height > highestHeight) {
                highestHeight = height;
                highestPos = counter;
            }
            if (height < lowestPos) {
                lowestHeight = height;
                lowestPos = counter;
            }
            counter++;
        }
        double gradient =  ((double) (highestHeight - lowestHeight) / (double) (highestPos - lowestPos));
        int inc = 0;
        for (int i = 0; i < 18; i++) {
            if (i < lowestPos || i > highestPos) {
                continue;
            }
            int newHeight = lowestHeight + (int) (inc * gradient);
            heights.set(i, newHeight);
            inc++;
        }
        return heights;
    }
}
