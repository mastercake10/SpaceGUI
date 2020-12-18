package xyz.spaceio.spaceitem;

import java.util.function.*;

public class ConditionalSpaceItemBuilder
{
    private Function<Object, SpaceItem> itemPredicate;
    
    public ConditionalSpaceItemBuilder() {
        super();
    }
    
    public void setBuilder(final Function<Object, SpaceItem> itemPredicate) {
        this.itemPredicate = itemPredicate;
    }
    
    public SpaceItem apply(final Object obj) {
        final SpaceItem spaceItem = this.itemPredicate.apply(obj);
        return spaceItem;
    }
}
