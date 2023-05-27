package xyz.spaceio.spacegui.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;

import xyz.spaceio.spacegui.SpaceGUI;
import xyz.spaceio.spacegui.helpers.StackBuilder;
import xyz.spaceio.spaceitem.DecorationMaterial;
import xyz.spaceio.spaceitem.SpaceItem;

public class GUIMultiPage {
	
	private List<SpaceGUI> pages = new ArrayList<>();
	private List<SpaceItem> itemList = new ArrayList<>();
	
	/*
	 * Navigation Items
	 */
	private SpaceItem itemPreviousPage = new SpaceItem().setStack(new StackBuilder(Material.ARROW).setDisplayname("&e<-"));
	private int slotItemPreviousPage = 9*6-9;
	
	private SpaceItem itemNextPage = new SpaceItem().setStack(new StackBuilder(Material.ARROW).setDisplayname("&e->"));
	private int slotItemNextPage = 9*6-1;
	
	private SpaceItem itemBackground = new SpaceItem().setStack(DecorationMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.get());
	
	// Items that will be added to the navigation bar
	private HashMap<Integer, SpaceItem> fixedOptionItems = new HashMap<>();
		
	private String titleFormat;
	

	public GUIMultiPage(String titleFormat) {
		this.titleFormat = titleFormat;
	}
	
	public SpaceItem getItemBackground() {
		return itemBackground;
	}

	public void setItemBackground(SpaceItem itemBackground) {
		this.itemBackground = itemBackground;
	}

	
	public void addItem(SpaceItem spaceItem) {
		itemList.add(spaceItem);
	}
	
	public void build() {
		int itemCnt = itemList.size();
		int pagesNeeded = (int) Math.ceil(itemCnt / (9 * 5.));
		
		Iterator<SpaceItem> itemIterator = itemList.iterator();
		
		// build a GUI for each page
		for (int page = 0; page < pagesNeeded; page++) {
			
			SpaceGUI spaceGUI = new SpaceGUI().size(9*6).title(String.format(titleFormat, page + 1, pagesNeeded));
			pages.add(spaceGUI);
			
			addNavigationItems(spaceGUI, page == 0, page == pagesNeeded - 1);
			
			// fill items
			for (int slot = 0; slot < 9*5; slot++) {
				
				// cancel when all items have been filled
				if (!itemIterator.hasNext()) {
					break;
				}
				
				SpaceItem spaceItem = itemIterator.next();
				itemIterator.remove();
				
				spaceGUI.addItem(spaceItem, slot);
			}
		}

	}
	
	private void addNavigationItems(SpaceGUI spaceGUI, boolean firstPage, boolean lastPage) {
		
		spaceGUI.fillBackground(itemBackground);
		
		SpaceItem previousPage = itemPreviousPage
				.addAction((p,a) -> this.getPage(this.getPageNum(a.view.spaceGUI)-1).ifPresent(gui -> gui.open(p)));
		
		if(!firstPage) {
			spaceGUI.addItem(previousPage, slotItemPreviousPage);	
		}
		
		SpaceItem nextPage = itemNextPage
				.addAction((p,a) -> this.getPage(this.getPageNum(a.view.spaceGUI)+1).ifPresent(gui -> gui.open(p)));
		
		if(!lastPage) {
			spaceGUI.addItem(nextPage, slotItemNextPage);	
		}
		
		this.fixedOptionItems.forEach((index, item) -> {
			spaceGUI.addItem(item, index + 9*5);
		});
		

	}
	
	public void addOptionItem(SpaceItem spaceItem, int slot) {
		fixedOptionItems.put(slot, spaceItem);
	}
	
	private int getPageNum(SpaceGUI spaceGUI) {
		return this.pages.indexOf(spaceGUI);
	}
	
	public Optional<SpaceGUI> getPage(int page) {
		
		if (pages.isEmpty()) {
			return Optional.empty();
		}
	
		return Optional.of(pages.get(page));
	}	
	public Optional<SpaceGUI> getFirstPage() {
		return this.getPage(0);
	}

	public List<SpaceGUI> getPages() {
		return pages;
	}

	public SpaceItem getItemPreviousPage() {
		return itemPreviousPage;
	}

	public void setItemPreviousPage(SpaceItem itemPreviousPage) {
		this.itemPreviousPage = itemPreviousPage;
	}

	public SpaceItem getItemNextPage() {
		return itemNextPage;
	}

	public void setItemNextPage(SpaceItem itemNextPage) {
		this.itemNextPage = itemNextPage;
	}

	public int getSlotItemPreviousPage() {
		return slotItemPreviousPage;
	}

	public void setSlotItemPreviousPage(int slotItemPreviousPage) {
		this.slotItemPreviousPage = slotItemPreviousPage;
	}

	public int getSlotItemNextPage() {
		return slotItemNextPage;
	}

	public void setSlotItemNextPage(int slotItemNextPage) {
		this.slotItemNextPage = slotItemNextPage;
	}
	
}
