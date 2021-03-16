package prison.customEconomy;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import prison.main.Main;

public class EconomyManager implements Listener {
	
	private Main main = null;
	private EconomyListener el = null;
	private Map<UUID,PlayerData> map = null;
	
	public EconomyManager(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents((Listener) this, main);
		el = new EconomyListener(main, this);
		main.getServer().getPluginManager().registerEvents((Listener) el, main);
		map = main.getFM().readPlayersData();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(!map.containsKey(e.getPlayer().getUniqueId())) {
			e.getPlayer().sendMessage(String.valueOf(map.containsKey(e.getPlayer().getUniqueId())));
			map.put(e.getPlayer().getUniqueId(), new PlayerData(main, e.getPlayer()));
		}
	}
	
	public void addPlayer(Player p) {
		map.put(p.getUniqueId(), new PlayerData(main, p));
	}
	
	public PlayerData getData(Player p) {
		return map.get(p.getUniqueId());
	}
	
	public PlayerData getData(String name) {
		for(Entry<UUID,PlayerData> e: map.entrySet()) {
			if(e.getKey().equals(Bukkit.getPlayer(name).getUniqueId())) {
				return e.getValue();
			}
		}
		return null;
	}
	
	public Map<UUID,PlayerData> getAllData() {
		return map;
	}
}
