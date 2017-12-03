package com.holub.color;

import java.awt.Color;

import com.holub.ui.Colors;

public class Yellow extends ColorSet{
	@Override
	public void setColorSetInfo() {
		// TODO Auto-generated method stub
		setBorderColor(Colors.DARK_YELLOW);
		setLiveColor(Color.RED);
		setDeadColor(Colors.LIGHT_YELLOW);
		setMenuName("Yellow (default)");
	}
}
