package xyz.spaceio.spaceitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum DecorationMaterial {
	WHITE_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 0, "WHITE_STAINED_GLASS_PANE", " "),
	ORANGE_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 1, "ORANGE_STAINED_GLASS_PANE", " "),
	MAGENTA_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 2, "MAGENTA_STAINED_GLASS_PANE", " "),
	LIGHT_BLUE_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 3, "LIGHT_BLUE_STAINED_GLASS_PANE", " "),
	YELLOW_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 4, "YELLOW_STAINED_GLASS_PANE", " "),
	LIME_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 5, "LIME_STAINED_GLASS_PANE", " "),
	PINK_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 6, "PINK_STAINED_GLASS_PANE", " "),
	GRAY_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 7, "GRAY_STAINED_GLASS_PANE", " "),
	LIGHT_GRAY_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 8, "LIGHT_GRAY_STAINED_GLASS_PANE", " "),
	CYAN_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 9, "CYAN_STAINED_GLASS_PANE", " "),
	PURPLE_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 10, "PURPLE_STAINED_GLASS_PANE", " "),
	BLUE_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 11, "BLUE_STAINED_GLASS_PANE", " "),
	BROWN_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 12, "BROWN_STAINED_GLASS_PANE", " "),
	GREEN_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 13, "GREEN_STAINED_GLASS_PANE", " "),
	RED_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 14, "RED_STAINED_GLASS_PANE", " "),
	BLACK_STAINED_GLASS_PANE ("STAINED_GLASS_PANE", (byte) 15, "BLACK_STAINED_GLASS_PANE", " ");

	String material12;
	byte damage12;
	String material13;
	
	String displayname;
	
	private DecorationMaterial(String material12, byte damage12, String material13, String displayname) {
		this.material12 = material12;
		this.damage12 = damage12;
		this.material13 = material13;
		this.displayname = displayname;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack get() {
		String bukkitVersion = Bukkit.getServer().getBukkitVersion().split("-")[0];

		int versionNum = Integer.parseInt(bukkitVersion.split("\\.")[1]);
		
		ItemStack itemStack;
		
		if(versionNum > 12) {
			itemStack = new ItemStack(Material.valueOf(material13));
		}else {
			
			itemStack =  new ItemStack(Material.valueOf(material12), 1, damage12);
		}
		
		if(displayname != null) {
			ItemMeta im = itemStack.getItemMeta();
			im.setDisplayName(displayname);
			itemStack.setItemMeta(im);
		}
		
		return itemStack;
	}
}
