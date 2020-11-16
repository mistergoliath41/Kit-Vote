package fr.mistergoliath.kitvote;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import fr.mistergoliath.kitvote.commands.KitVote;
import fr.mistergoliath.kitvote.utils.managers.ConfigManager;
import fr.mistergoliath.kitvote.utils.managers.VoteManager;

public class Main extends JavaPlugin {
	
	private VoteManager kv = new VoteManager();
	private ConfigManager defaultConfig = new ConfigManager(new File("config.yml"));
	
	@Override
	public void onEnable() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
			saveDefaultConfig();
		}
		getCommand("kitvote").setExecutor(new KitVote(this));
	}
	
	public VoteManager getVoteManager() {
		return this.kv;
	}
	
}
