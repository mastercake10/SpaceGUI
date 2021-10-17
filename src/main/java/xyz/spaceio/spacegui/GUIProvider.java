package xyz.spaceio.spacegui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import xyz.spaceio.spaceitem.SpaceItem;

public class GUIProvider {
	
	private static HashMap<Player, GUIView> registeredGUIs = new HashMap<>();

	public static void registerPlugin(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
		
		ConfigurationSerialization.registerClass(SpaceItem.class);
		ConfigurationSerialization.registerClass(SpaceGUI.class);
	}
	
	public static void registerView(GUIView guiView) {
		if(registeredGUIs.containsKey(guiView.viewer)) {
			
			if(!guiView.getPreviousView().isPresent()) {
				// don't update if view has already a previous gui to prevent circles
				guiView.setPreviousView(registeredGUIs.get(guiView.viewer));				
			}
		}
		registeredGUIs.put(guiView.viewer, guiView);
	}
	
	public static Set<GUIView> getRegisterdViews(){
		return new HashSet<>(registeredGUIs.values());
	}
	
	public static Optional<GUIView> getViewByInventory(Inventory inventory){
		Optional<GUIView> gui = GUIProvider.getRegisterdViews().stream().filter(g -> g.matchesInventory(inventory)).findFirst();
		return gui;
	}
	
	static void destroy(GUIView view) {
		GUIProvider.destroyView(view.viewer);
	}
	
	static void destroyView(Player player) {
		registeredGUIs.remove(player);
	}
}
