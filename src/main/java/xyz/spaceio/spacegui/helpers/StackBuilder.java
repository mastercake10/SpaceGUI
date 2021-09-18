package xyz.spaceio.spacegui.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import xyz.spaceio.spaceitem.DecorationMaterial;

public class StackBuilder {
	
	private Material type;
	private int amount = 1;
	private byte data = (byte) 0;
	
	private String displayname;
	private List<String> lore;
	
	private boolean formatColorCodes = true;
	private DecorationMaterial decorationMaterial;
	
	
	public StackBuilder(Material material) {
		this.type = material;
	}
	
	public StackBuilder(DecorationMaterial decorationMaterial) {
		this.decorationMaterial = decorationMaterial;
	}
	
	public StackBuilder setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public StackBuilder setDisplayname(String displayname) {
		this.displayname = displayname;
		return this;
	}
	
	public StackBuilder setLore(List<String> formatLore, String toInsert) {
		return this.setLore(formatLore.stream()
				.map(line -> String.format(line, toInsert))
				.collect(Collectors.toList()));
	}
	
	public StackBuilder setLore(List<String> lore) {
		this.lore = new LinkedList<String>(lore);
		return this;
	}
	
	public StackBuilder setLore(String... lines) {
		this.setLore(Arrays.asList(lines));
		return this;
	}
	
	public StackBuilder addToLore(String... lines) {
		for (String line : lines) {
			this.lore.add(line);
		}
		return this;
	}
	
	public StackBuilder wrapLore() {
		lore = lore.stream().map(s -> WordUtils.wrap(s, 32).split(System.lineSeparator())).flatMap(Arrays::stream).collect(Collectors.toList());

		return this;
	}
	
	public StackBuilder setDoFormatColorCodes(boolean formatColorCodes) {
		this.formatColorCodes = formatColorCodes;
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack build() {
		ItemStack itemStack;
		
		if(decorationMaterial != null) {
			itemStack = decorationMaterial.get();
		} else {
			itemStack = new ItemStack(type, amount);
			
			if(data != 0) {
				itemStack = new ItemStack(type, amount, data);
			}
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
