package prison.customEconomy;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import prison.main.*;

@SuppressWarnings("unused")
public class PlayerData {
	
	private Main main = null;
	private Utils u = null;
	private EconomyUtils eu = null;
	
	private String name = null;
	private InetSocketAddress IP = null;
	private UUID UUID = null;
	
	private BigInteger bal = null;
	private BigInteger gems = null;
	private BigInteger tokens = null;
	
	public PlayerData(Main main, Player p) {
		this.main = main;
		u = new Utils();
		eu = EconomyUtils.getIstance(main);
		name = p.getName();
		UUID = p.getUniqueId();
		bal = new BigInteger("10000");
		gems = new BigInteger("10000");
		tokens = new BigInteger("10000");
	}
	
	public PlayerData(Main main, OfflinePlayer p, BigInteger bal, BigInteger gems, BigInteger tokens) {
		this.main = main;
		u = new Utils();
		eu = EconomyUtils.getIstance(main);
		name = p.getName();
		UUID = p.getUniqueId();
		this.bal = bal;
		this.gems = gems;
		this.tokens = tokens;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(name);
	}
	
	public BigInteger getBalanceOf(String type) {
		if(type.equalsIgnoreCase("money")) {
			return bal;
		} else if(type.equalsIgnoreCase("gems")) {
			return gems;
		} else if(type.equalsIgnoreCase("tokens")) {
			return tokens;
		} else {
			return null;
		}
	}
	
	public String getBal() {
		return bal.toString();
	}
	
	public String getGems() {
		return gems.toString();
	}
	
	public String getTokens() {
		return tokens.toString();
	}
	
	// Override
	public void totalBalChat() {
		getPlayer().sendMessage(u.chat(eu.getPP()+"&7Your current total balance is:"));
		getPlayer().sendMessage(u.chat(" &f• "+"&6Money&7: &f"+getBal()+"&6$"));
		getPlayer().sendMessage(u.chat(" &f• "+"&aGems&7: &f"+getGems()+eu.getGemsSymbol()));
		getPlayer().sendMessage(u.chat(" &f• "+"&bTokens&7: &f"+getTokens()+eu.getTokenSymbol()));
	}
	
	// Override
	public void totalBalChat(Player p) {
		p.sendMessage(u.chat(eu.getPP()+"&f"+name+"&7's current total balance is:"));
		p.sendMessage(u.chat(" &f• "+"&6Money&7: &f"+getBal()+"&6$"));
		p.sendMessage(u.chat(" &f• "+"&aGems&7: &f"+getGems()+eu.getGemsSymbol()));
		p.sendMessage(u.chat(" &f• "+"&bTokens&7: &f"+getTokens()+eu.getTokenSymbol()));
	}
	
	// Override
	public void moneyBalChat() {
		getPlayer().sendMessage(u.chat(eu.getPP()+"&7Your current &6money&7 balance is &f"+getBal()+"&6$"));
	}
	
	// Override
	public void moneyBalChat(Player p) {
		p.sendMessage(u.chat(eu.getPP()+"&f"+name+"&7's current &6money&7 balance is &f"+getBal()+"&6$"));
	}
	
	// Override
	public void gemsBalChat() {
		getPlayer().sendMessage(u.chat(eu.getPP()+"&7Your current &agems&7 balance is &f"+getGems()+eu.getGemsSymbol()));
	}
	
	// Override
	public void gemsBalChat(Player p) {
		p.sendMessage(u.chat(eu.getPP()+"&f"+name+"&7's current &agems&7 balance is &f"+getGems()+eu.getGemsSymbol()));
	}
	
	// Override
	public void tokensBalChat() {
		getPlayer().sendMessage(u.chat(eu.getPP()+"&7Your current &btokens&7 balance is &f"+getTokens()+eu.getTokenSymbol()));
	}
	
	// Override
	public void tokensBalChat(Player p) {
		p.sendMessage(u.chat(eu.getPP()+"&f"+name+"&7's current &btokens&7 balance is &f"+getTokens()+eu.getTokenSymbol()));
	}
	
	public void pay(String type, int val) {
		if(type.equalsIgnoreCase("money")) {
			bal = bal.add(eu.itoBI(val));
			getPlayer().sendMessage(u.chat(eu.getPP()+"&f"+val+"&6$ &7Have been added to your balance."));
		} else if(type.equalsIgnoreCase("gems")) {
			gems = gems.add(eu.itoBI(val));
			getPlayer().sendMessage(u.chat(eu.getPP()+"&f"+val+eu.getGemsSymbol()+" &7Have been added to your balance."));
		} else if(type.equalsIgnoreCase("tokens")) {
			tokens = tokens.add(eu.itoBI(val));
			getPlayer().sendMessage(u.chat(eu.getPP()+"&f"+val+eu.getTokenSymbol()+" &7Have been added to your balance."));
		}
	}
	
	public void take(String type, int val) {
		if(type.equalsIgnoreCase("money")) {
			if(bal.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough money to do that!"));
				return;
			} else {
				bal = bal.subtract(eu.itoBI(val));
				getPlayer().sendMessage(u.chat(eu.getNP()+"&f"+val+"&6$ &7Have been taken from your balance."));
			}
		} else if(type.equalsIgnoreCase("gems")) {
			if(gems.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough gems to do that!"));
				return;
			} else {
				gems = gems.subtract(eu.itoBI(val));
				getPlayer().sendMessage(u.chat(eu.getNP()+"&f"+val+eu.getGemsSymbol()+" &7Have been taken from your balance."));
			}
		} else if(type.equalsIgnoreCase("tokens")) {
			if(tokens.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough tokens to do that!"));
				return;
			} else {
				tokens = tokens.subtract(eu.itoBI(val));
				getPlayer().sendMessage(u.chat(eu.getNP()+"&f"+val+eu.getTokenSymbol()+" &7Have been taken from your balance."));
			}
		}
	}
	
	public ItemStack withdraw(String type, int val) {
		String curr = null;
		String displayName = null;
		String line0 = "&7Withdrawn by "+name;
		String line1 = "&7Amount: ";
		if(type.equalsIgnoreCase("money")) {
			if(bal.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough money to do that!"));
				return null;
			} else {
				take(type, val);
				curr = "&6Money";
				line1 = line1.concat("&f"+val+"&6$");
			}
		} else if(type.equalsIgnoreCase("gems")) {
			if(gems.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough gems to do that!"));
				return null;
			} else {
				take(type, val);
				curr = "&aGem";
				line1 = line1.concat("&f"+val+eu.getGemsSymbol());
			}
		} else if(type.equalsIgnoreCase("tokens")) {
			if(tokens.compareTo(eu.itoBI(val)) < 0) {
				getPlayer().sendMessage(u.chat(eu.getNP()+"%cYou don't have enough tokens to do that!"));
				return null;
			} else {
				take(type, val);
				curr = "&bToken";
				line1 = line1.concat("&f"+val+eu.getTokenSymbol());
			}
		}
		displayName = u.chat(curr+" &fnote");
		getPlayer().sendMessage(u.chat(eu.getPP()+"&7You received a "+displayName+"&7!"));
		ItemStack receipt = u.createItem(Material.PAPER, 1, u.chat(displayName), line0, line1);
		receipt.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		ItemMeta meta = receipt.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		receipt.setItemMeta(meta);
		return receipt;
	}
	
	public void redeem(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		ArrayList<String> lore = (ArrayList<String>) meta.getLore();
		String type = null;
		String sub = meta.getDisplayName().substring(2, 5);
		
		if(sub.equals("Mon")) {
			type = "money";
		} else if(sub.equals("Gem")) {
			type = "gems";
		} else if(sub.equals("Tok")) {
			type = "tokens";
		}
		String line1 = lore.get(1);
		int val = 0;
		if(line1.substring(2).startsWith("Amount: ")) {
			val = Integer.parseInt(line1.substring(12, line1.length()-3));
		}
		pay(type, val);
	}

	

	
	//-------------------------------
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InetSocketAddress getIP() {
		return IP;
	}

	public void setIP(InetSocketAddress iP) {
		IP = iP;
	}

	public UUID getUUID() {
		return UUID;
	}

	public void setUUID(UUID uUID) {
		UUID = uUID;
	}

	public void setBal(BigInteger bal) {
		this.bal = bal;
	}

	public void setGems(BigInteger gems) {
		this.gems = gems;
	}

	public void setTokens(BigInteger tokens) {
		this.tokens = tokens;
	}
}
