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
		this.items.put(slot, spaceItem);
		return this;
	}
	
    public SpaceGUI addRow(SpaceItem spaceItem, int row) {
        if (row == -1) {
            row = this.size / 9 - 1;
        }
        for (int i = row * 9; i < row * 9 + 9; ++i) {
            this.addItem(spaceItem, i);
        }
        return this;
    }
	
	public SpaceGUI build() {
		this.inventory = Bukkit.createInventory(null, size, title);
		
		items.forEach((slot, item) -> {
			this.inventory.setItem(slot, item.getItemStack());
		});
		
		GUIProvider.registerGUI(this);
		return this;
	}
	
	public void open(Player p) {
		p.openInventory(this.inventory);
	}
	
	public boolean matchesInventory(Inventory inventory) {
		return this.inventory.equals(inventory);
	}

	public void onClick(InventoryClickEvent e) {
		if(!this.items.containsKey(e.getSlot())) return;
		
		this.items.get(e.getSlot()).performActions((Player) e.getWhoClicked());
		
		e.setCancelled(true);
	}
}
