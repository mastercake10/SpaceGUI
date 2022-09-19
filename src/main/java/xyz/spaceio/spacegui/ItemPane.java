package xyz.spaceio.spacegui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.spaceio.spaceitem.ClickAction;
import xyz.spaceio.spaceitem.SpaceItem;

public class ItemPane {
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
		if(slot == -1) {
			slot = this.sizeX * this.sizeY - 1;
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
				item.format(player);
				itemStacks.put(slot, item.getFormattedItemStack());
			}
		});
		System.out.println("lol");
		
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
	
   
}
