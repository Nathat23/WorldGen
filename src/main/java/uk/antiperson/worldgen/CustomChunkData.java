package uk.antiperson.worldgen;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

public class CustomChunkData {

    private final ChunkGenerator.ChunkData chunkData;

    public CustomChunkData(ChunkGenerator.ChunkData data) {
        this.chunkData = data;
    }

    public void setMaterial(int x, int y, int z, Material material) {
        chunkData.setBlock(x, y, z, material);
    }

    public void setLots(int x, int yMin, int yMax, int z, Material material) {
        for (int a = yMin; a <= yMax; a++) {
            chunkData.setBlock(x,a,z,material);
        }
    }

}
