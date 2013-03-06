package me.shock.playervaults.util;

import java.io.File;
import java.io.IOException;

import me.shock.playervaults.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class VaultManager {

	public Main plugin;
	public VaultManager(Main instance) {
		this.plugin = instance;
	}
	
	String title;
	private final String directory = "plugins" + File.separator + "PlayerVaults" + File.separator + "vaults";

	/**
	 * Method to save player's vault.
	 * Serialize his inventory.
	 * Save the vaults.yml
	 * @param player
	 * @throws IOException 
	 */
	public void saveVault(Inventory inv, String player, int number) throws IOException {
			// Get the player's file and serialize the inventory.
			String ser = Serialization.toBase64(inv);
			YamlConfiguration yaml = playerVaultFile(player);
			yaml.set("vault" + number + "", ser);
			saveFile(player, yaml);
	}

	/**
	 * Method to load player's vault.
	 * Deserialize his inventory
	 * 
	 * TODO: Check to see if the path exists before we get it!
	 */
	public void loadVault(CommandSender sender, String holder, int number) {
		YamlConfiguration playerFile = playerVaultFile(holder);
		String data = playerFile.getString("vault"+number);
		Player player = (Player) sender;
		if(data == null) {
			Inventory inv = Bukkit.createInventory(player, 54, ChatColor.DARK_RED + "Vault #"+String.valueOf(number));
			player.openInventory(inv);
		} else {
			Inventory inv = Serialization.fromBase64(data);
			player.openInventory(inv);
		}
	}

	public void deleteVault(CommandSender sender, String target, int number) throws IOException {
		String name = target.toLowerCase();
		File file = new File(directory + name + ".yml");
		FileConfiguration playerFile = YamlConfiguration.loadConfiguration(file);
		if(file.exists()) {
			ConfigurationSection section = playerFile.getConfigurationSection("vault" + number);
			section.set(null, null);
			sender.sendMessage(title + "Deleting " + ChatColor.GREEN + " " + number);
			playerFile.save(file);
		}
		else {
			sender.sendMessage(title + " That doesn't exist!");
		}
	}

	public YamlConfiguration playerVaultFile(String player) {
		File folder = new File(directory);
		if(!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(directory + File.separator + player.toLowerCase() + ".yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration playerFile = YamlConfiguration.loadConfiguration(file);
		return playerFile;
	}
	
	public void saveFile(String name, YamlConfiguration yaml) throws IOException {
		File file = new File(directory + File.separator + name.toLowerCase() + ".yml");
		yaml.save(file);
	}
}
