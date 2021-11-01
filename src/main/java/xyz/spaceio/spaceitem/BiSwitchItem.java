package xyz.spaceio.spaceitem;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BiSwitchItem extends SpaceItem {
	private Supplier<Boolean> supplierIsEnabled;
	
	private ItemStack itemOn;
	private ItemStack itemOff;
	
	private BiConsumer<Player, ClickAction> actionOnEnable;
	private BiConsumer<Player, ClickAction> actionOnDisable;

	
	public BiSwitchItem(Supplier<Boolean> supplierIsEnabled, SpaceItem itemOn, SpaceItem itemOff) {
		super();
		this.supplierIsEnabled = supplierIsEnabled;
		this.itemOn = itemOn.getItemStack();
		this.itemOff = itemOff.getItemStack();
		
		this.setStack(new ItemStack(Material.AIR));
		this.update(this.getItemStack());
		
		super.addAction((p, c) -> {
			if(supplierIsEnabled != null && supplierIsEnabled.get()) {
				if(actionOnDisable != null) {
					actionOnDisable.accept(p, c);
				}
			} else {
				if(actionOnEnable != null) {
					actionOnEnable.accept(p, c);
				}
			}
			
			this.update(c.clickEvent.getCurrentItem());
		});
	}
	
    public BiSwitchItem onEnable(BiConsumer<Player, ClickAction> action) {
    	this.actionOnEnable = action;
    	return this;
    }
    
    public BiSwitchItem onDisable(BiConsumer<Player, ClickAction> action) {
    	this.actionOnDisable = action;
    	return this;
    }
	
	private void update(ItemStack itemStackClicked) {
		ItemStack currentItemStack = itemStackClicked;
		ItemStack newItemStack = this.itemOff;
		
		if(supplierIsEnabled != null && supplierIsEnabled.get()) {
			newItemStack = this.itemOn;
		}

		currentItemStack.setType(newItemStack.getType());
		currentItemStack.setAmount(newItemStack.getAmount());
		currentItemStack.setDurability(newItemStack.getDurability());
		
		if(newItemStack.hasItemMeta()) {
			currentItemStack.setItemMeta(newItemStack.getItemMeta());
		}
		
	}
  
}
