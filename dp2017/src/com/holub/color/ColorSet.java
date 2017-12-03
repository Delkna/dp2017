package com.holub.color;

import java.awt.Color;

public abstract class ColorSet{
	private Color BORDER_COLOR;
	private Color LIVE_COLOR;
	private Color DEAD_COLOR;
	private String MENU_NAME;
	
	public ColorSet() {
		setColorSetInfo();
	}
	
	public abstract void setColorSetInfo();
	
	public Color getBorderColor() {
		return BORDER_COLOR;
	}
	public Color getLiveColor() {
		return LIVE_COLOR;
	}
	public Color getDeadColor() {
		return DEAD_COLOR;
	}
	public String getMenuName() {
		return MENU_NAME;
	}
	
	public void setBorderColor(Color border) {
		BORDER_COLOR = border;
	}
	public void setLiveColor(Color live) {
		LIVE_COLOR = live;
	}
	public void setDeadColor(Color dead) {
		DEAD_COLOR = dead;
	}
	public void setMenuName(String menuName) {
		MENU_NAME = menuName;
	}	
}