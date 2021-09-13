package xyz.spaceio.spacegui;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import xyz.spaceio.spaceitem.SpaceItem;

public class GUIView {
	public Inventory inventory;
	public SpaceGUI spaceGUI;
	Player viewer;
	
	private GUIView previousView;
	
	public GUIView(Player viewer, SpaceGUI spaceGUI, Inventory inventory) {
		this.viewer = viewer;
		this.spaceGUI = spaceGUI;
		this.inventory = inventory;
		
		this.getPreviousView();
	}
	
	public Optional<GUIView> getPreviousView() {

		return Optional.ofNullable(previousView);
	}
	
	public boolean matchesInventory(Inventory inventory) {
		return this.inventory.equals(inventory);
	}

	public void onClick(InventoryClickEvent e) {
		spaceGUI.onClick(e, this);
	}
	
	public void update(SpaceItem spaceItem) {
		ItemStack updatedItemStack = spaceItem.getFormatted(viewer);
		inventory.setItem(spaceGUI.getSlot(spaceItem), updatedItemStack);
	}

	public void setPreviousView(GUIView guiView) {
		this.previousView = guiView;
		
	}
	
	public void show() {
		// register the view for making it functional
		GUIProvider.registerView(this);
		
		viewer.openInventory(this.inventory);
	}

	public SpaceGUI getSpaceGUI() {
		return spaceGUI;
	}
	
}
