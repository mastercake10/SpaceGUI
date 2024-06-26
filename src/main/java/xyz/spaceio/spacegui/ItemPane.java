package xyz.spaceio.spacegui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.spaceio.spaceitem.SpaceItem;

public class ItemPane implements ConfigurationSerializable {
	private Map<Integer, SpaceItem> items = new HashMap<>();
	private SpaceItem backGroundItem;

	private int locX, locY;
	private int sizeX, sizeY;
	
	private List<ItemPane> itemPanes = new ArrayList<>();
	
	
	public ItemPane(int sizeX, int sizeY, int locX, int locY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.locX = locX;
		this.locY = locY;
	}
	
	public void addItem(SpaceItem spaceItem, int slot) {
		if(slot < 0) {
			slot = this.sizeX * this.sizeY + slot;
		}
		this.items.put(slot, spaceItem);
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
	
    public void addRow(SpaceItem spaceItem, int row) {
        if (row == -1) {
            row = this.sizeY - 1;
        }
        for (int i = row * this.sizeX; i < row * this.sizeX + this.sizeX; ++i) {
            this.addItem(spaceItem, i);
        }
    }
    
    public void fillBackground(SpaceItem spaceItem) {
    	this.backGroundItem = spaceItem;
    }
    
    public void addAll(Map<Integer, SpaceItem> items) {
    	this.items = items;
    }
    
    protected Map<Integer, SpaceItem> getItems() {
    	return items;
    }
    
    public void setSize(int sizeX, int sizeY) {
    	this.sizeX = sizeX;
    	this.sizeY = sizeY;
    }
    
    public HashMap<Integer, ItemStack> getItemStacks(Player player) {
    	HashMap<Integer, ItemStack> itemStacks = new HashMap<>();
    	
		if(backGroundItem != null) {
			backGroundItem.format(player);
			for(int i = 0; i < this.sizeX * this.sizeY; i++) {
				itemStacks.put(i, backGroundItem.getItemStack());
			}
		}
		
		items.forEach((slot, item) -> {
			if(item != null) {
				if(item.getDynamicStack() != null) {
					itemStacks.put(slot, item.getItemStack());
				} else {
					item.format(player);
					itemStacks.put(slot, item.getFormattedItemStack());
				}
			}
		});
		
		itemPanes.forEach(pane -> {
			HashMap<Integer, ItemStack> paneItemStacks = pane.getItemStacks(player);
			paneItemStacks.forEach((relSlot, stack) -> {
				int relY = relSlot / pane.sizeY;
				int relX = relSlot % pane.sizeX;
				
				int absY = relY + pane.locY;
				int absX = relX + pane.locX;

				int absSlot = absY*this.sizeX + absX;
				itemStacks.put(absSlot, stack);
			});
		});
		
		return itemStacks;
    }
    
    public void addItemPane(ItemPane itemPane) {
    	this.itemPanes.add(itemPane);
    }

	public void clearItems() {
		this.items.clear();
	}

	public static ItemPane deserialize(Map<String, Object> map) {
		ItemPane itemPane = new ItemPane(9, 6, 0, 0);
		deserializeValues(itemPane, map);

		return itemPane;
	}

	public static void deserializeValues(ItemPane itemPane, Map<String, Object> map) {
		itemPane.addAll((Map<Integer, SpaceItem>) map.get("items"));
		itemPane.fillBackground((SpaceItem) map.get("background"));
	}

	public static SpaceGUI valueOf(Map<String, Object> map) {
		return SpaceGUI.deserialize(map);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", items);
		map.put("background", backGroundItem);

		return map;
	}
}
