package dev.cardinal.betteranticheat.datacollector.spigot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class BetterAntiCheatDataCollector extends JavaPlugin implements org.bukkit.event.Listener {
    private static BetterAntiCheatDataCollector plugin;
    @Override
    public void onLoad() {
        plugin = this;
        this.getLogger().info("DataCollector for BetterAntiCheat Loading");
    }

    @Override
    public void onEnable() {
        plugin = this;
        Data.filepath = this.getDataFolder().getPath() + "/data.csv";
        if (!this.getDataFolder().exists()) {
            try {
                this.getDataFolder().mkdir();
                File csv = new File(Data.filepath);
                csv.createNewFile();
                Data.data += "distance,in_water,in_lava,elytra\n";
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        this.getLogger().info("\u001B[33m ██████╗  █████╗  ██████╗");
        this.getLogger().info("\u001B[33m ██╔══██╗██╔══██╗██╔════╝");
        this.getLogger().info("\u001B[33m ██████╔╝███████║██║     ");
        this.getLogger().info("\u001B[33m ██╔══██╗██╔══██║██║     ");
        this.getLogger().info("\u001B[33m ██████╔╝██║  ██║╚██████╗");
        this.getLogger().info("\u001B[33m ╚═════╝ ╚═╝  ╚═╝ ╚═════╝");
        this.getLogger().info("\u001B[34m        BAC LOADED       ");
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        try {
            FileWriter fw = new FileWriter(Data.filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Data.data);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        String line;

        Location from = event.getFrom();
        Location to = event.getTo();
        Double distance = from.distance(to);
        Boolean in_water = event.getPlayer().getLocation().getBlock().getType() == Material.WATER;
        Boolean in_lava = event.getPlayer().getLocation().getBlock().getType() == Material.LAVA;
        Boolean elytra;
        if (event.getPlayer().getInventory().getChestplate() != null) {
            elytra = event.getPlayer().getInventory().getChestplate().getType() == Material.ELYTRA;
        } else {
            elytra = false;
        }
        if (from!=null && to!=null && distance!=null && in_lava!=null && in_water!=null && elytra!=null) {
            line = String.valueOf(distance) + "," + String.valueOf(in_water) + "," + String.valueOf(in_lava) + "," + String.valueOf(elytra);
            Data.data += (line + "\n");
        }
        }


}
