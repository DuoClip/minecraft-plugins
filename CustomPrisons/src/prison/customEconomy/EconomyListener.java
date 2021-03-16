package prison.customEconomy;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import prison.main.Main;
import prison.main.Utils;

public class EconomyListener implements Listener, CommandExecutor {
	
	private Main main = null;
	private EconomyManager em = null;
	private Utils u = null;
	
	public EconomyListener(Main main, EconomyManager em) {
		this.main = main;
		this.em = em;
		u = new Utils();
		initCommands();
	}
	
	private void initCommands() {
		main.getCommand("bal").setExecutor(this);
		main.getCommand("withdraw").setExecutor(this);
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		EquipmentSlot es = e.getHand();
        if (es.equals(EquipmentSlot.HAND)) {
        	Player p = e.getPlayer();
    		Action a = e.getAction();
    		if(a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK)) {
    			ItemStack i = p.getInventory().getItemInMainHand();
    			if(i.getType().equals(Material.PAPER) && i.containsEnchantment(Enchantment.DURABILITY)) {
    				if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 100) {
    					p.getInventory().getItemInMainHand().setAmount(i.getAmount()-1);
    					em.getData(p).redeem(i);
    				}
    			}
    		}
        }
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			// Bal command
			if(cmd.getName().equalsIgnoreCase("bal")) {
				if(args.length == 0) {
					em.getData(p).totalBalChat();
				} else if(args.length == 1) {
					String s = args[0];
					if(s.equalsIgnoreCase("money")) {
						em.getData(p).moneyBalChat();
					} else if(s.equalsIgnoreCase("gems")) {
						em.getData(p).gemsBalChat();
					}  else if(s.equalsIgnoreCase("tokens")) {
						em.getData(p).tokensBalChat();
					} else if(s.equals(p.getName())) {
						em.getData(p).totalBalChat();
					} else if(em.getData(s) != null) {
						em.getData(s).totalBalChat(p);
					} else {
						p.sendMessage(u.chat("&cPlayer not found!"));
					}
				} else if(args.length == 2) {
					String s = args[0];
					String name = args[1];
					if(name.equals(p.getName())) {
						if(s.equalsIgnoreCase("money")) {
							em.getData(p).moneyBalChat();
						} else if(s.equalsIgnoreCase("gems")) {
							em.getData(p).gemsBalChat();
						}  else if(s.equalsIgnoreCase("tokens")) {
							em.getData(p).tokensBalChat();
						}
					} else if(em.getData(name) != null) {
						if(s.equalsIgnoreCase("money")) {
							em.getData(name).moneyBalChat(p);
						} else if(s.equalsIgnoreCase("gems")) {
							em.getData(name).gemsBalChat(p);
						}  else if(s.equalsIgnoreCase("tokens")) {
							em.getData(name).tokensBalChat(p);
						}
					} else {
						p.sendMessage(u.chat("&cPlayer not found!"));
					}
				}
				return true;
			}
			
			// Withdraw command
			if(cmd.getName().equals("withdraw")) {
				if(args.length == 2) {
					PlayerData data = em.getData(p);
					ItemStack receipt = data.withdraw(args[0], Integer.parseInt(args[1]));
					if(receipt != null) {
						p.getInventory().addItem(receipt);
						EconomyUtils.getIstance(main).successMessage(p);
					} else {
						EconomyUtils.getIstance(main).failMessage(p);
					}
				}
				return true;
			}
		}
		return false;
	}
}
