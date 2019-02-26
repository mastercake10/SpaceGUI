package xyz.spaceio.spacegui;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class GUIProvider {
	
	private static Set<SpaceGUI> registeredGUIs = new HashSet<SpaceGUI>();
	
	public static void registerPlugin(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
	}
	
	public static void registerGUI(SpaceGUI spaceGUI) {
		registeredGUIs.add(spaceGUI);
	}
	
	public static Set<SpaceGUI> getRegisterdGUIs(){
		return registeredGUIs;
	}
	
	public static Optional<SpaceGUI> getGUIOf(Inventory inventory){
		Optional<SpaceGUI> gui = registeredGUIs.stream().filter(g -> g.matchesInventory(inventory)).findFirst();
		return gui;
	}
}
