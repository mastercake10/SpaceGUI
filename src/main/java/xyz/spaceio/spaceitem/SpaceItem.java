package xyz.spaceio.spaceitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.spaceio.spacegui.helpers.StackBuilder;

public class SpaceItem implements ConfigurationSerializable {
	
    private ItemStack itemStack;
    protected List<BiConsumer<Player, ClickAction>> actions;
    private String label;
    
    private Function<Player, Object>[] format;
    private Supplier<StackBuilder> dynamicStack;
    private ItemStack formattedItem;
  

	public SpaceItem() {
        super();
        this.actions = new ArrayList< BiConsumer<Player, ClickAction>>();
    }
	
    public SpaceItem setStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }
    
    public SpaceItem setStack(Supplier<StackBuilder> stackBuilder) {
    	this.dynamicStack = stackBuilder;
    	return this;
    }
    
    public SpaceItem setStack(StackBuilder stackBuilder) {
        this.setStack(stackBuilder.build());
        return this;
    }
    
    public SpaceItem setStack(Material material) {
    	StackBuilder stackBuilder = new StackBuilder(material);
    	this.setStack(stackBuilder);
    	
    	return this;
    }
    
    public void formatLore(final Object... objects) {
        if (this.itemStack.hasItemMeta() && this.itemStack.getItemMeta().hasLore()) {
            final String toFormat = (String)this.itemStack.getItemMeta().getLore().stream().collect(Collectors.joining(".###."));
            final String formatted = String.format(toFormat, objects);
            final ItemMeta im = this.itemStack.getItemMeta();
            im.setLore(Arrays.stream(formatted.split(".###.")).collect(Collectors.toList()));
            this.itemStack.setItemMeta(im);
        }
    }
    
    public SpaceItem addAction(final BiConsumer<Player, ClickAction> action) {
        this.actions.add(action);
        return this;
    }
    
    public SpaceItem clearActions() {
    	this.actions.clear();
    	return this;
    }
    
    public void performActions(final Player player, final ClickAction action) {
        this.actions.forEach(c -> c.accept(player, action));
    }
    
    public ItemStack getFormatted(Player player) {
    	if(this.format != null) {
        	if(this.itemStack.hasItemMeta() && this.itemStack.getItemMeta().hasDisplayName()) {
        		ItemMeta itemMeta = itemStack.getItemMeta();
        		
        		Object[] formatArgs = Arrays.stream(this.format).map(function -> function.apply(player)).toArray(Object[]::new);
        		
        		itemMeta.setDisplayName(String.format(itemMeta.getDisplayName(), formatArgs));
        		ItemStack cloned = this.itemStack.clone();
        		cloned.setItemMeta(itemMeta);

        		return cloned;
        	}	
    	}
    	return this.getItemStack();
    }
    
    public void format(Player player) {
    	formattedItem = this.getFormatted(player);
    }
    
    public ItemStack getItemStack() {
    	if(this.dynamicStack != null) {
    		return this.dynamicStack.get().build();
    	}
    	return this.itemStack;
    }
    
    public ItemStack getFormattedItemStack() {
        return this.formattedItem;
    }
    
    public String getLabel() {
		return label;
	}

	public SpaceItem setLabel(String label) {
		this.label = label;
		return this;
	}
	
	public SpaceItem setFormat(Function<Player, Object>[] format) {
		this.format = format;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public SpaceItem setFormat(Function<Player, Object> format) {
		Function<Player, Object>[] functions = new Function[] { format };
		return this.setFormat(functions);
		// return this.setFormat(Collections.singletonList(format).toArray(Function[]::new));
	}
	
//	public SpaceItem setFormat(Function<Player, Object> format) {
//		Function<Player, Object[]> format2 = p -> new Object[] {format.apply(p)};
//		this.format = format2 ;
//		return this;
//	}

    public static SpaceItem deserialize(Map<String, Object> map) {
        return new SpaceItem()
        				.setStack((ItemStack) map.get("itemstack"))
        				.setLabel((String) map.get("label"));
    }

    public static SpaceItem valueOf(Map<String, Object> map) {
        return SpaceItem.deserialize(map);
    }
    
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("label", label);
		map.put("itemstack", itemStack);
		
		return map;
	}


}
