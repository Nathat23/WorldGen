package uk.antiperson.worldgen;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public final class WorldGen extends JavaPlugin implements CommandExecutor, Listener {

    boolean enabled = true;

    @Override
    public void onEnable() {
        if (!enabled) {
            return;
        }
        // Plugin startup logic
        getCommand("worldgen").setExecutor(this);

        int nextId = getId() + 1;
        for (Player player :Bukkit.getOnlinePlayers()) {
            Bukkit.getScheduler().runTaskLater(this, () ->Bukkit.dispatchCommand(player, "mvcreate testing" + nextId + " NORMAL -g worldgen"), 20);
            Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.dispatchCommand(player, "mvtp testing" + nextId), 50);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                player.setGameMode(GameMode.CREATIVE);
                player.teleport(new Location(Bukkit.getWorld("testing" + nextId), 58,97,-1553, -21,30));
                player.setFlying(true);
            }, 200);
        }

    }

    public int getId() {
        int counter = 0;
        for (File file : new File(getServer().getPluginsFolder().getAbsolutePath().substring(0, getServer().getPluginsFolder().getAbsolutePath().lastIndexOf('/'))).listFiles()) {
            System.out.println(file.getName());
            if (!file.isDirectory()) continue;
            if (!file.getName().contains("testing")) continue;
            counter = Math.max(counter, Integer.parseInt(file.getName().replace("testing", "")));
        }
        return counter;
    }

    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return new CustomWorldGen(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.dispatchCommand(sender, "mvdelete testing" + getId());
        Bukkit.dispatchCommand(sender, "mvconfirm");
        Bukkit.dispatchCommand(sender, "reload confirm");
        return false;
    }


}
