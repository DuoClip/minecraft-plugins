package prison.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import prison.customEconomy.PlayerData;

public class FilesManager {
	
	private Main main = null;
	
	public FilesManager(Main main) {
		this.main = main;
	}
	
	public Map<String,Location> readWarps() {
		Map<String,Location> map = new HashMap<String,Location>();
		World world = main.getServer().getWorld("world");
		String read = null;
		String name = null;
		Location l = null;
		String[] content = null;
		String[] coords = null;
		
		String dir = "\\CustomPrison\\warps.txt";
		String nativeDir = null;
		File f = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			nativeDir = FilenameUtils.getFullPathNoEndSeparator(new File(FilesManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath());
			f = new File(nativeDir + dir);
			if(!f.exists()) {
				f.getParentFile().mkdir();
				f.createNewFile();
			}
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			while((read = br.readLine()) != null) {
				content = read.split(";");
				name = content[0];
				coords = content[1].split(",");
				l = new Location(world, Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
				map.put(name, l);
			}
			
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void writeWarps(Map<String,Location> map) {
		
		Set<String> keys = map.keySet();
		String[] names = new String[keys.size()];
		keys.toArray(names);
		
		Collection<Location> values = map.values();
		Location[] locs = new Location[values.size()]; 
		values.toArray(locs);
		
		String dir = "\\CustomPrison\\warps.txt";
		String nativeDir = null;
		File f = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			nativeDir = FilenameUtils.getFullPathNoEndSeparator(new File(FilesManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath());
			f = new File(nativeDir + dir);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			for(int c = 0; c < map.size(); c++) {
				bw.append(names[c]+";"+locs[c].getX()+","+locs[c].getY()+","+locs[c].getZ()+"\n");
			}
			
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<UUID,PlayerData> readPlayersData() {
		Map<UUID,PlayerData> map = new HashMap<UUID,PlayerData>();
		String read = null;
		String[] content = null;
		UUID uuid = null;
		String[] details = null;
		PlayerData data = null;
		
		String dir = "\\CustomPrison\\playersData.txt";
		String nativeDir = null;
		File f = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			nativeDir = FilenameUtils.getFullPathNoEndSeparator(new File(FilesManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath());
			f = new File(nativeDir + dir);
			if(!f.exists()) {
				f.getParentFile().mkdir();
				f.createNewFile();
			}
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			while((read = br.readLine()) != null) {
				content = read.split(";");
				uuid = UUID.fromString(content[0]);
				
				details = content[1].split(",");
				data = new PlayerData(main, Bukkit.getOfflinePlayer(uuid), new BigInteger(details[0]), new BigInteger(details[1]), new BigInteger(details[2]));
				map.put(uuid, data);
			}
			
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void writePlayersData(Map<UUID,PlayerData> map) {
		
		Set<UUID> keys = map.keySet();
		UUID[] uuids = new UUID[keys.size()];
		keys.toArray(uuids);
		
		Collection<PlayerData> values = map.values();
		PlayerData[] details = new PlayerData[values.size()]; 
		values.toArray(details);
		
		String dir = "\\CustomPrison\\playersData.txt";
		String nativeDir = null;
		File f = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			nativeDir = FilenameUtils.getFullPathNoEndSeparator(new File(FilesManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath());
			f = new File(nativeDir + dir);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
			for(int c = 0; c < map.size(); c++) {
				bw.append(uuids[c].toString()+";"+details[c].getBal()+","+details[c].getGems()+","+details[c].getTokens()+"\n");
			}
			
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
