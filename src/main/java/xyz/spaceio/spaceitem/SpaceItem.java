package xyz.spaceio.spaceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpaceItem {
	private ItemStack itemStack;
	private List<Consumer<Player>> actions = new ArrayList<Consumer<Player>>();
	
	public SpaceItem setStack(ItemStack itemStack) {
		this.itemStack = itemStack;
		return this;
	}
	
	public void addAction(Consumer<Player> action) {
		actions.add(action);
	}
	
	public void performActions(Player player) {
		actions.forEach(c -> c.accept(player));
	}

	public ItemStack getItemStack() {
		return itemStack;
	}
}
