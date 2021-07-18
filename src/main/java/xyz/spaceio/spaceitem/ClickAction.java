package xyz.spaceio.spaceitem;

import org.bukkit.event.inventory.InventoryClickEvent;

import xyz.spaceio.spacegui.GUIView;

public class ClickAction {
	public InventoryClickEvent clickEvent;
	public GUIView view;
	
	public ClickAction(InventoryClickEvent clickEvent, GUIView view) {
		this.clickEvent = clickEvent;
		this.view = view;
	}
	
	public SpaceItem getClickedItem() {
		// TODO
		return null;
	}
	
	public GUIView getView() {
		return this.view;
	}
	
}
