package xyz.spaceio.spaceitem;

import org.bukkit.inventory.*;
import java.util.function.*;
import org.bukkit.entity.*;
import java.util.stream.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class SpaceItem
{
    private ItemStack itemStack;
    private List<Consumer<Player>> actions;
    
    public SpaceItem() {
        super();
        this.actions = new ArrayList<Consumer<Player>>();
    }
    
    public SpaceItem setStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
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
    
    public SpaceItem addAction(final Consumer<Player> action) {
        this.actions.add(action);
        return this;
    }
    
    public void performActions(final Player player) {
        this.actions.forEach(c -> c.accept(player));
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }

}
