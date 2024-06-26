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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import xyz.spaceio.spaceitem.ClickAction;
import xyz.spaceio.spaceitem.SpaceItem;

public class SpaceGUI extends ItemPane implements ConfigurationSerializable{
	private int size = 9;
	private String title = "";
	
	private long cooldownMillis = 200;
	private List<BiConsumer<Player, ClickAction>> clickActions = new ArrayList<>();
	
	public SpaceGUI() {
		super(9, 6, 0, 0);
	}
	
	
	/**
	 * Sets the size of the GUI and parent ItemFrame. Must be multiply of 9.
	 * @param size
	 * @return
	 */
	public SpaceGUI size(int size) {
		this.size = size - (size % 9);
		
		this.setSize(9, size / 9);
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


	public Inventory update(Player player) {
		Inventory inventory = Bukkit.createInventory(null, size, title);

		super.setSize(9, this.size);

		HashMap<Integer, ItemStack> itemStacks = super.getItemStacks(player);
		itemStacks.forEach((slot, itemStack) -> {
			if (slot < inventory.getSize()) {
				inventory.setItem(slot, itemStack);
			}
		});

		return inventory;
	}

	public GUIView build(Player player) {
		Inventory inventory = this.update(player);

		return new GUIView(player, this, inventory);
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
		
		Map<Integer, SpaceItem> items = super.getItems();
		// TODO: Calculate relative pane coordinates
		if(!items.containsKey(e.getSlot())) return;

		if(e.getClickedInventory() != null && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			items.get(e.getSlot()).performActions((Player) e.getWhoClicked(), new ClickAction(e, view));
		}
	}
	
    public static SpaceGUI deserialize(Map<String, Object> map) {
		SpaceGUI gui = new SpaceGUI()
				.title((String) map.get("title"))
				.size(((int) map.get("rows")) * 9);

		ItemPane.deserializeValues(gui, map);

		return gui;
	}

    public static SpaceGUI valueOf(Map<String, Object> map) {
        return SpaceGUI.deserialize(map);
    }

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("rows", size / 9);

		// add ItemPane fields
		map.putAll(super.serialize());

		return map;
	}
}
