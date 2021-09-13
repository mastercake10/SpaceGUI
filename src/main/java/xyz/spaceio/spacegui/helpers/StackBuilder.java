package xyz.spaceio.spacegui.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class StackBuilder {
	
	private Material type;
	private int amount = 1;
	private byte data = (byte) 0;
	
	private String displayname;
	private List<String> lore;
	
	private boolean formatColorCodes = true;
	
	
	StackBuilder(Material material) {
		this.type = material;
	}
	
	public StackBuilder setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public StackBuilder setDisplayname(String displayname) {
		this.displayname = displayname;
		return this;
	}
	
	public StackBuilder setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public StackBuilder setLore(String... lines) {
		this.setLore(Arrays.asList(lines));
		return this;
	}
	
	public StackBuilder setDoFormatColorCodes(boolean formatColorCodes) {
		this.formatColorCodes = formatColorCodes;
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack build() {
		ItemStack itemStack = new ItemStack(type, amount);
		
		if(data != 0) {
			itemStack = new ItemStack(type, amount, data);
		
		}
		
		ItemMeta itemMeta = itemStack.getItemMeta();
		if(displayname != null) {
			if(formatColorCodes) {
				displayname = ChatColor.translateAlternateColorCodes('&', displayname);
			}
			itemMeta.setDisplayName(displayname);
		}
		
		if(lore != null) {
			if(formatColorCodes) {
				lore = lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
			}
			itemMeta.setLore(lore);
		}
		
		itemStack.setItemMeta(itemMeta);
		
		
		return itemStack;
	}
	
	
}
