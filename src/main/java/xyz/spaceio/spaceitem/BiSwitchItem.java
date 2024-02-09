package xyz.spaceio.spaceitem;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BiSwitchItem extends SpaceItem {
	private final Supplier<Boolean> supplierIsEnabled;

	private final ItemStack itemOn;
	private final ItemStack itemOff;

	private BiConsumer<Player, ClickAction> actionOnEnable;
	private BiConsumer<Player, ClickAction> actionOnDisable;

	
	public BiSwitchItem(Supplier<Boolean> supplierIsEnabled, SpaceItem itemOn, SpaceItem itemOff) {
		super();
		this.supplierIsEnabled = supplierIsEnabled;
		this.itemOn = itemOn.getItemStack();
		this.itemOff = itemOff.getItemStack();
		
		this.setStack(new ItemStack(Material.AIR));
		this.update();
		
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
			
			this.update();
			if(c.clickEvent.getCurrentItem() != null) {
				c.clickEvent.getCurrentItem().setType(this.getItemStack().getType());
				c.clickEvent.getCurrentItem().setItemMeta(this.getItemStack().getItemMeta());
			}
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
	
	private void update() {
		if(supplierIsEnabled != null && supplierIsEnabled.get()) {
			this.setStack(() -> this.itemOn);
		} else {
			this.setStack(() -> this.itemOff);
		}
	}
  
}
