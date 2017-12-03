package com.holub.color;

import java.awt.Color;

import com.holub.ui.Colors;

public class Dark extends ColorSet{
	@Override
	public void setColorSetInfo() {
		// TODO Auto-generated method stub
		setBorderColor(Color.BLACK);
		setLiveColor(Color.WHITE);
		setDeadColor(Color.BLACK);
		setMenuName("Dark");
	}
}
