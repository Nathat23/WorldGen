package uk.antiperson.worldgen;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import uk.antiperson.worldgen.biome.WorldBiome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomChunk {

    private final int chunkX;
    private final int chunkZ;
    private final World world;
    private final ChunkHeightMap heightMap;
    private final ChunkBiomeMap chunkBiomeMap;
    private final Set<SimpleBlock> populatedBlock;
    private boolean terrainGenerated;

    public CustomChunk(World world, int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.world = world;
        this.heightMap = new ChunkHeightMap();
        this.chunkBiomeMap = new ChunkBiomeMap();
        this.populatedBlock = new HashSet<>();
        this.terrainGenerated = false;
    }

    public Chunk getChunk() {
        return getWorld().getChunkAt(getX(), getX());
    }

    public ChunkHeightMap getHeightMap() {
        return heightMap;
    }

    public ChunkBiomeMap getBiomeMap() {
        return chunkBiomeMap;
    }

    public int getX() {
        return chunkX;
    }

    public int getZ() {
        return chunkZ;
    }

    public int getWorldX(int offset) {
        return (getX() * 16) + offset;
    }

    public int getWorldZ(int offset) {
        return (getZ() * 16) + offset;
    }

    public World getWorld() {
        return world;
    }

    public void addPopulatedBlock(SimpleBlock blockGroup) {
        populatedBlock.add(blockGroup);
    }

    public Set<SimpleBlock> getPopulatedBlock() {
        return populatedBlock;
    }

    public void clearPopulatedBlocks() {
        populatedBlock.clear();
    }

    public boolean isTerrainGenerated() {
        return terrainGenerated;
    }

    public void setTerrainGenerated(boolean terrainGenerated) {
        this.terrainGenerated = terrainGenerated;
    }

    public class ChunkHeightMap {

        private final List<Integer> heightValues;

        public ChunkHeightMap() {
            heightValues = new ArrayList<>(256);
        }

        public int getHeightAt(int x, int z) {
            return heightValues.get(getIndex(x, z));
        }

        private int getIndex(int x, int z) {
            return (x * 16) + z;
        }

        public int getHighestInRegion(int minX, int minZ, int maxX, int maxZ) {
            int highest = 0;
            for (int x = minX; x < maxX; x++) {
                for (int z = minZ; z < maxZ; z++) {
                    int highestAt = getHeightAt(x,z);
                    if (highestAt > highest) {
                        highest = highestAt;
                    }
                }
            }
            return highest;
        }

        public List<Integer> getHeightValues() {
            return heightValues;
        }

        public Block getHighestBlockAt(int x, int z) {
            Location location = new Location(getWorld(), getWorldX(x), getHeightAt(x, z), getWorldZ(z));
            return location.getBlock();
        }

        public void addHeight(int height) {
            if (heightValues.size() > 256) {
                throw new RuntimeException("More values than allowed have been added to heightmap list!");
            }
            heightValues.add(height);
        }

        public void setHeight(int x, int z, int newHeight) {
            int id = getIndex(x, z);
            if (id > 255) {
                throw new RuntimeException("Tried to update heightmap with value outside chunk boundaries!");
            }
            heightValues.set(id, newHeight);
        }

    }

    public class ChunkBiomeMap {

        private final List<WorldBiome> biomes;

        public ChunkBiomeMap() {
            this.biomes = new ArrayList<>(256);
        }

        private int getIndex(int x, int z) {
            return (x * 16) + z;
        }

        public WorldBiome getBiomeType(int x, int z) {
            return biomes.get(getIndex(x, z));
        }

        public List<WorldBiome> getBiomes() {
            return biomes;
        }

        public void addBiome(WorldBiome type) {
            if (biomes.size() > 256) {
                throw new RuntimeException("More values than allowed have been added to biome map list!");
            }
            biomes.add(type);
        }
    }
}
