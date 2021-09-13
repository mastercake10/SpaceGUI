package xyz.spaceio.spaceitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum DecorationMaterial {
	BACKGROUND_GRAY ("STAINED_GLASS_PANE", (byte) 7, "GRAY_STAINED_GLASS_PANE", " "),
	BACKGROUND_WHITE ("STAINED_GLASS_PANE", (byte) 0, "WHITE_STAINED_GLASS_PANE", " ");
	
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
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        
		int versionNum = Integer.parseInt(version.substring(3, 5).replace("_", ""));
		
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
