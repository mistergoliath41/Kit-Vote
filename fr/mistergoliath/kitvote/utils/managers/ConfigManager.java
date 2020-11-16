package fr.mistergoliath.kitvote.utils.managers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ConfigManager {

	private File configFile;

	private YamlConfiguration yamlConfig;
	
	public ConfigManager(File configFile) {
		this.configFile = configFile;
	}
	
	public ConfigManager createConfig() {
		configFile = new File(getDataFolder() + File.separator + configFile.getName());
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public YamlConfiguration getConfig() {
		configFile = new File(getDataFolder() + File.separator + configFile.getName());
		yamlConfig = YamlConfiguration.loadConfiguration(configFile);
		return yamlConfig;
	}
	
	public <T> ConfigManager set(String valueLocation, T value) {
		try {
			configFile = new File(getDataFolder() + File.separator + configFile.getName());
			yamlConfig = YamlConfiguration.loadConfiguration(configFile);
			yamlConfig.set(valueLocation, value);
			yamlConfig.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public <T> List<ItemStack> getList(String valueLocation) {
		try {
			configFile = new File(getDataFolder() + File.separator + configFile.getName());
			yamlConfig = YamlConfiguration.loadConfiguration(configFile);
			return (List<ItemStack>)yamlConfig.getList(valueLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ConfigManager saveConfig() {
		try {
			File file = new File(getDataFolder().getAbsolutePath() + File.separator + getConfigFile().getName());
			if (!file.exists()) Files.copy(ConfigManager.class.getResourceAsStream("/" + getConfigFile().getName()), file.getAbsoluteFile().toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public File getConfigFile() {
		return configFile;
	}

	public ConfigManager setConfigFile(File configFile) {
		this.configFile = configFile;
		return this;
	}
	
	public File getDataFolder() {
		return getDataFolder(getPluginName());
	}
	
	public File getDataFolder(String name) {
		return Bukkit.getPluginManager().getPlugin(name).getDataFolder();
	}
	
	public String getPluginName() {
		String name = null;
		Scanner sc = null;
		try {
			sc = new Scanner(getClass().getResourceAsStream("/plugin.yml"));
			while (sc.hasNext()) {
				String s = sc.nextLine();
				if (s != null && s.startsWith("name:")) {
					name = s.split(" ")[1];
				}
			}
			return name;
		} catch (Exception e) {
			return null;
		} finally {
			if (sc != null) sc.close();
		}
	}

}
