package xyz.spaceio.spacegui.helpers;

import java.util.ArrayList;
import java.util.List;

import xyz.spaceio.spacegui.SpaceGUI;
import xyz.spaceio.spaceitem.SpaceItem;

public class Row implements Helper{
	private List<SpaceItem> itemList = new ArrayList<>();
	private int rowNum = 0;
	private Align align;
	
	public enum Align {
		SPACE_BETWEEN, LEFT, RIGHT
	}
	
	public Row(int rowNum, Align align) {
		this.rowNum = rowNum;
		this.align = align;
	}
	
	public Row addItem(SpaceItem spaceItem) {
		if(itemList.size() > 8) {
			return this;
		}
		
		itemList.add(spaceItem);
		return this;
	}
	
	final int[] PATTERN = new int[] {0b00001111, 0b00110111, 0b01001001, 0b00010111, 0b10101010, 0b00010111, 0b01001001, 0b00110111, 0b00001111};

	@Override
	public void apply(SpaceGUI gui) {
		if(itemList.size() == 9 || itemList.size() == 0) {
			align = Align.LEFT;
		}
		
		switch(align) {
			case LEFT:
				int idx = 0;
				for(SpaceItem item : itemList) {
					gui.addItem(item, rowNum * 9 + idx);
					idx++;
				}
				break;
			case RIGHT:
				idx = 9 - itemList.size();
				for(SpaceItem item : itemList) {
					gui.addItem(item, rowNum * 9 + idx);
					idx++;
				}
				break;
			case SPACE_BETWEEN:
				int cnt = 0;
				for (int i = 0; i < 9; i++) {
				    if((PATTERN[i] >> (8-itemList.size()) & 1) != 0) {
				    	gui.addItem(itemList.get(cnt), i + rowNum * 9);
				    	
				    	cnt++;
				    }
				}
				break;
				
		}
		
	}
}
