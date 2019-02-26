package xyz.spaceio.spacegui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import xyz.spaceio.spaceitem.SpaceItem;

public class SpaceGUI {
	private int size = 9;
	private String title = "";
	
	private Inventory inventory;
	
	private Map<Integer, SpaceItem> items = new HashMap<>();
	
	public SpaceGUI() {
		
	}
	
	/**
	 * Sets the size of the GUI
	 * @param size
	 * @return
	 */
	public SpaceGUI size(int size) {
		this.size = size - (9 - size % 9);
		return this;
	}
	
	public SpaceGUI title(String title) {
		this.title = title;
		return this;
	}
	
	public SpaceGUI addItem(SpaceItem spaceItem, int slot) {
		items.put(slot, spaceItem);
		return this;
	}
	
	public SpaceGUI build() {
		this.inventory = Bukkit.createInventory(null, size, title);
		
		items.forEach((slot, item) -> {
			inventory.setItem(slot, item.getItemStack());
		});
		
		GUIProvider.registerGUI(this);
		return this;
	}
	
	public boolean matchesInventory(Inventory inventory) {
		return this.inventory.equals(inventory);
	}

	public void onClick(InventoryClickEvent e) {
		if(!items.containsKey(e.getSlot())) return;
		
		items.get(e.getSlot()).performActions((Player) e.getWhoClicked());
		
		e.setCancelled(true);
	}
}
