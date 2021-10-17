package xyz.spaceio.spaceitem;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.spaceio.spaceitem.ClickAction;
import xyz.spaceio.spaceitem.SpaceItem;

public class BiSwitchItem extends SpaceItem {
	private Supplier<Boolean> supplierIsEnabled;
	
	private ItemStack itemOn;
	private ItemStack itemOff;
	

	
	public BiSwitchItem(Supplier<Boolean> supplierIsEnabled, SpaceItem itemOn, SpaceItem itemOff) {
		super();
		this.supplierIsEnabled = supplierIsEnabled;
		this.itemOn = itemOn.getItemStack();
		this.itemOff = itemOff.getItemStack();
		
		this.setStack(new ItemStack(Material.AIR));
		this.update(this.getItemStack());
		super.addAction((p, c) -> {
			this.update(c.clickEvent.getCurrentItem());
		});
	}
	
    @Override
    public SpaceItem addAction(final BiConsumer<Player, ClickAction> action) {
        this.actions.add(0, action);
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
		
		if(newItemStack.hasItemMeta()) {
			currentItemStack.setItemMeta(newItemStack.getItemMeta());
		}
		
	}
  
}
