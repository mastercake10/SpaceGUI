package xyz.spaceio.spacegui;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GUIListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GUIProvider.getViewByInventory(e.getWhoClicked().getOpenInventory().getTopInventory()).ifPresent(g -> g.onClick(e));
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Optional<GUIView> guiView = GUIProvider.getViewByInventory(e.getInventory());
		
		guiView.ifPresent(GUIProvider::destroy);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		GUIProvider.destroyView(e.getPlayer());
	}
}
