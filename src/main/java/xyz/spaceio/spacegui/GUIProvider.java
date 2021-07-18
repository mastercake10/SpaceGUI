package xyz.spaceio.spacegui;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import xyz.spaceio.spaceitem.SpaceItem;

public class GUIProvider {
	
	private static Set<GUIView> registeredGUIs = new HashSet<GUIView>();
	
	public static void registerPlugin(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
		
		ConfigurationSerialization.registerClass(SpaceItem.class);
		ConfigurationSerialization.registerClass(SpaceGUI.class);
	}
	
	public static void registerView(GUIView guiView) {
		registeredGUIs.add(guiView);
	}
	
	public static Set<GUIView> getRegisterdViews(){
		return registeredGUIs;
	}
	
	public static Optional<GUIView> getViewByInventory(Inventory inventory){
		Optional<GUIView> gui = registeredGUIs.stream().filter(g -> g.matchesInventory(inventory)).findFirst();
		return gui;
	}
	
	static void destoryView(GUIView view) {
		registeredGUIs.remove(view);
	}
}
