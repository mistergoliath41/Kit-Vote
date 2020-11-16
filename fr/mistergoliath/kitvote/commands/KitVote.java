package fr.mistergoliath.kitvote.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.mistergoliath.kitvote.Main;
import fr.mistergoliath.kitvote.utils.MessageUtils;
import fr.mistergoliath.kitvote.utils.managers.VoteManager;

public class KitVote implements CommandExecutor {
	
	private Main main;

	public KitVote(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			Player p = (Player)cs;
			if (args.length == 0) {
				if (p.hasPermission("kitvote.use")) {
					if (!getVoteManager().canVote(p)) {
						getVoteManager().vote(p);
						return true;
					} else {
						MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.you_cant_vote").replace("%time%", getVoteManager().getFormattedTime(getVoteManager().getTimeRemaining(p))));
						return true;
					}
				}
			} else if (args.length >= 1) {
				switch (args[0].toLowerCase()) {
					case "reset":
						if (!p.hasPermission("kitvote.reset")) {
							MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.permission_denied"));
							return false;
						}
						if (args.length == 2 && Bukkit.getServer().getPlayer(args[1]) != null) {
							Player t = Bukkit.getServer().getPlayer(args[1]);
							getVoteManager().resetTime(t);
							MessageUtils.sendFormatedMessage(p, getConfig().getString("messages.vote_time_reset").replace("%player%", t.getName()));
							MessageUtils.sendFormatedMessage(t, getConfig().getString("messages.you_can_now_vote"));
							return true;
						}
						break;
		
					default:
						break;
				}
			}
		}
		return false;
	}
	
	private FileConfiguration getConfig() {
		return this.main.getConfig();
	}
	
	public VoteManager getVoteManager() {
		return this.main.getVoteManager();
	}

}
