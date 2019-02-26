package xyz.spaceio.spacegui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GUIProvider.getGUIOf(e.getClickedInventory()).ifPresent(g -> g.onClick(e));
	}
}
