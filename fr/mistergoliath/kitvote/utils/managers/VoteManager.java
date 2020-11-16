package fr.mistergoliath.kitvote.utils.managers;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.mistergoliath.kitvote.utils.MessageUtils;

public class VoteManager extends ConfigManager {
	
	private ConfigManager defaultConfig = new ConfigManager(new File("config.yml"));
	
	public VoteManager() {
		super(new File("votes.yml"));
	}
	
	public boolean canVote(Player p) {
		return getVoteTime(p) >= System.currentTimeMillis();
	}

	public VoteManager vote(Player p) {
		int quantity = defaultConfig.getConfig().getInt("kitvote.quantity");
		set(p.getName() + ".time", + (long)(System.currentTimeMillis() + defaultConfig.getConfig().getLong("kitvote.interval") * 1000L));
		p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, quantity));
		MessageUtils.sendFormatedMessage(p, defaultConfig.getConfig().getString("messages.you_got_a_reward").replace("%quantity%", Integer.toString(quantity)));
		return this;
	}
	
	public long getTimeRemaining(Player p) {
		return getVoteTime(p) - System.currentTimeMillis();
	}
	
	public long getVoteTime(Player p) {
		return getConfig().getLong(p.getName() + ".time");
	}
	
	public VoteManager resetTime(Player p) {
		set(p.getName() + ".time", 0);
		return this;
	}
	
	public String getFormattedTime(long millis) {
		return String.format("%02dh %02dm %02ds", TimeUnit.MILLISECONDS.toHours(millis),
							 TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
							 TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

}
