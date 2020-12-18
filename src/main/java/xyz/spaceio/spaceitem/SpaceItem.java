package xyz.spaceio.spaceitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpaceItem
{
    private ItemStack itemStack;
    private List<BiConsumer<Player, ItemStack>> actions;
    
    public SpaceItem() {
        super();
        this.actions = new ArrayList< BiConsumer<Player, ItemStack>>();
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
    
    public SpaceItem addAction(final BiConsumer<Player, ItemStack> action) {
        this.actions.add(action);
        return this;
    }
    
    public void performActions(final Player player, final ItemStack itemStack) {
        this.actions.forEach(c -> c.accept(player, itemStack));
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }

}
