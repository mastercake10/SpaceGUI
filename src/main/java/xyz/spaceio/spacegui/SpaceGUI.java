package xyz.spaceio.spacegui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import xyz.spaceio.spaceitem.ClickAction;
import xyz.spaceio.spaceitem.SpaceItem;

public class SpaceGUI implements ConfigurationSerializable{
	private int size = 9;
	private String title = "";
	
	private Map<Integer, SpaceItem> items = new HashMap<>();
	private SpaceItem backGroundItem;
	
	private long cooldownMillis = 200;
	
	private List<BiConsumer<Player, ClickAction>> clickActions = new ArrayList<>();
	
	public SpaceGUI() {
		
	}
	
	
	/**
	 * Sets the size of the GUI
	 * @param size
	 * @return
	 */
	public SpaceGUI size(int size) {
		this.size = size - (size % 9);
		return this;
	}
	
	public SpaceGUI title(String title) {
		this.title = title;
		return this;
	}
	
	public long getCooldownMillis() {
		return cooldownMillis;
	}


	public void setCooldownMillis(long cooldownMillis) {
		this.cooldownMillis = cooldownMillis;
	}


	public SpaceGUI addItem(SpaceItem spaceItem, int slot) {
		if(slot == -1) {
			slot = size - 1;
		}
		this.items.put(slot, spaceItem);
		return this;
	}
	
	public SpaceItem getOrCreateItem(SpaceItem spaceItem, int slot) {
		SpaceItem si = this.getItemWithLabel(spaceItem.getLabel());
		if(si != null) {
			return si;
		} else {
			addItem(spaceItem, slot);
		}
		
		return spaceItem;
	}
	
	public SpaceItem getItemWithLabel(String label) {
		if(label == null)
			return null;
		
		for(SpaceItem item : items.values()) {
			if(item.getLabel() != null && item.getLabel().equals(label)) {
				return item;
			}
		}
		return null;
	}
	
	public SpaceItem getItem(int slot) {
		return this.items.get(slot);
	}
	
	public int getSlot(SpaceItem item) {
		for(Entry<Integer, SpaceItem> entry : items.entrySet()) {
			if(item.getLabel() != null && entry.getValue().getLabel() != null) {
				if(item.getLabel().equals(entry.getValue().getLabel())) {
					return entry.getKey();
				}
			}
		}
		return -1;
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
    
    public SpaceGUI fillBackground(SpaceItem spaceItem) {
    	this.backGroundItem = spaceItem;
        return this;
    }
    
    public SpaceGUI addAll(Map<Integer, SpaceItem> items) {
    	this.items = items;
    	return this;
    }
	
	public GUIView build(Player player) {
		Inventory inventory = Bukkit.createInventory(null, size, title);
		
		if(backGroundItem != null) {
			backGroundItem.format(player);
			for(int i = 0; i < this.size; i++) {
				inventory.setItem(i, backGroundItem.getItemStack());
			}			
		}
		
		items.forEach((slot, item) -> {
			if(item != null) {
				item.format(player);
				inventory.setItem(slot, item.getFormattedItemStack());
			}
		});
		GUIView view = new GUIView(player, this, inventory);
		
		return view;
	}
	
	public void open(Player player) {
		GUIView view = this.build(player);
		view.show();
	}
	
	public void addAction(final BiConsumer<Player, ClickAction> action) {
		clickActions.add(action);		
	}
	

	public void onClick(InventoryClickEvent e, GUIView view) {
		this.clickActions.forEach(consumer -> consumer.accept((Player) e.getWhoClicked(), new ClickAction(e, view)));
		
		// handle item click
		if(!this.items.containsKey(e.getSlot())) return;
		
		this.items.get(e.getSlot()).performActions((Player) e.getWhoClicked(), new ClickAction(e, view));
	}
	
    public static SpaceGUI deserialize(Map<String, Object> map) {
        return new SpaceGUI()
        				.title((String) map.get("title"))
        				.size(((int) map.get("rows")) * 9)
        				.addAll((Map<Integer, SpaceItem>) map.get("items"))
        				.fillBackground((SpaceItem) map.get("background"));
    }

    public static SpaceGUI valueOf(Map<String, Object> map) {
        return SpaceGUI.deserialize(map);
    }
    
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", items);
		map.put("background", backGroundItem);
		map.put("title", title);
		map.put("rows", size / 9);
	
		
		return map;
	}
}
