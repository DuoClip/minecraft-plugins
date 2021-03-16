package prison.customWarps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import prison.customGUI.GUIManager;
import prison.customGUI.GUIUtils;
import prison.main.Main;
import prison.main.Utils;

@SuppressWarnings({"unused"})
public class WarpExecutor implements CommandExecutor {
	
	private Main main = null;
	private Utils u = null;
	private GUIManager gm = null;
	private WarpManager wm = null;
	
	public WarpExecutor(Main main, WarpManager wm) {
		this.main = main;
		u = new Utils();
		gm = main.getGM();
		this.wm = wm;
		initCommands();
	}
	
	private void initCommands() {
		main.getCommand("spawn").setExecutor(this);
		main.getCommand("crates").setExecutor(this);
		main.getCommand("pvpmine").setExecutor(this);
		main.getCommand("warp").setExecutor(this);
		main.getCommand("setwarp").setExecutor(this);
		main.getCommand("delwarp").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			switch(args.length) {
			case(0):
				switch(cmd.getName()) {
				case("warp"):
					p.openInventory(gm.getInv("warps", p));
					return true;
				case("setwarp"):
					wm.createWarp(p, args);
					return true;
				case("delwarp"):
					wm.removeWarp(args[0]);
					return true;
				default:
					wm.tp(p, cmd.getName());
					return true;
				}
			case(1):
				if(cmd.getName().equals("warp")) {
					wm.tp(p, args[0]);
					return true;
				} else {
					p.sendMessage(u.chat("&4&oError! Too many arguments!"));
					return true;
				}
			case(4):
				if(cmd.getName().equals("setwarp")) {
					wm.createWarp(p, args);
					return true;
				} else {
					p.sendMessage(u.chat("&4&oError! Too many arguments!"));
					return true;
				}
			default:
				p.sendMessage(u.chat("&4&oError! Too many arguments!"));
				return true;
			}
		}
		return false;
	}
}
