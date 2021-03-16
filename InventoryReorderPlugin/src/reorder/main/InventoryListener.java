package reorder.main;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
	
	Map<Material,Integer> map = null;
	InventoryClickEvent e = null;
	Player p = null;
	
	public InventoryListener(Main main, Map<Material,Integer> map) {
		this.map = map;
	}
	
	@EventHandler
	public void onMouseWheelClick(InventoryClickEvent e) {
		if(e.getClick().equals(ClickType.MIDDLE)) {
			if(e.getWhoClicked() instanceof Player) {
				p = (Player) e.getWhoClicked();
				this.e = e;
				reorder(inv());
			}
		}
	}
	
	private Inventory inv(){
		return e.getClickedInventory();
	}

	private void reorder(Inventory inv) {
		int c = 0;
		int start = 0;
		int finish = inv.getSize();
		ItemStack[] contentsArr = inv.getContents();
		ArrayList<ItemStack> contentsList = new ArrayList<ItemStack>();
		ArrayList<ItemStack> ordered = new ArrayList<ItemStack>();
		
		if(inv.equals(p.getInventory())) {
			start += 9;
			finish -= 5;
		}
		
		for(c = start; c < finish; c++) {
			if(contentsArr[c] != null) {
				contentsList.add(contentsArr[c]);
			}
		}
		
		
		int[] ids = new int[contentsList.size()];
		c = 0;
		for(ItemStack i: contentsList) {
			ids[c] = map.get(i.getType());
			c++;
		}
		
		quickSort(ids, 0, ids.length-1);
		
		for(int v = 0; v < ids.length; v++) {
			for(ItemStack i: contentsList) {
				if(map.get(i.getType()).equals(ids[v])) {
					ordered.add(i);
					contentsList.remove(contentsList.indexOf(i));
					break;
				}
			}
		}
		
		for(c = 0; c < ordered.size()-1; c++) {
			ItemStack i = ordered.get(c);
			ItemStack n = ordered.get(c+1);
			if(i.getAmount() < i.getMaxStackSize()) {
				if(i.getType() == n.getType()) {
					int amount = i.getAmount() + n.getAmount();
					if(amount < i.getMaxStackSize()) {
						ordered.get(c).setAmount(amount);
						ordered.remove(c+1);
						c--;
					} else if(amount == i.getMaxStackSize()) {
						ordered.get(c).setAmount(amount);
						ordered.remove(c+1);
					} else {
						amount -= i.getMaxStackSize();
						ordered.get(c).setAmount(i.getMaxStackSize());
						ordered.get(c+1).setAmount(amount);
					}
				}
			}
		}
		
		for(c = start; c < finish; c++) {
			inv().setItem(c, new ItemStack(Material.AIR, 0));
			if(c-start < ordered.size()) {
				inv().setItem(c, ordered.get(c-start));
			}
		}
		p.updateInventory();
	}
	
	public void quickSort(int arr[], int begin, int end) {
	    if (begin < end) {
	        int partitionIndex = partition(arr, begin, end);
	 
	        quickSort(arr, begin, partitionIndex-1);
	        quickSort(arr, partitionIndex+1, end);
	    }
	}
	
	private int partition(int arr[], int begin, int end) {
	    int pivot = arr[end];
	    int i = (begin-1);
	    for (int j = begin; j < end; j++) {
	        if (arr[j] <= pivot) {
	            i++;
	            int swapTemp = arr[i];
	            arr[i] = arr[j];
	            arr[j] = swapTemp;
	        }
	    }
	    int swapTemp = arr[i+1];
	    arr[i+1] = arr[end];
	    arr[end] = swapTemp;
	    return i+1;
	}
}
