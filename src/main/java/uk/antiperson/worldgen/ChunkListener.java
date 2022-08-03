package uk.antiperson.worldgen;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener implements Listener {

    private CustomWorldGen customWorldGen;

    public ChunkListener(CustomWorldGen customWorldGen) {
        this.customWorldGen = customWorldGen;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        CustomChunk chunk = null;
        for (CustomChunk customChunk : customWorldGen.getCustomChunks()) {
            if (customChunk.getX() == event.getChunk().getX() && customChunk.getZ() == event.getChunk().getZ() && customChunk.getWorld().equals(event.getChunk().getWorld())) {
                chunk = customChunk;
                break;
            }
        }
        if (chunk != null) {
            customWorldGen.getCustomChunks().remove(chunk);
        }
    }
}
