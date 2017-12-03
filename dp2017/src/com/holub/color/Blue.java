package com.holub.color;

import java.awt.Color;

public class Blue extends ColorSet{
	@Override
	public void setColorSetInfo() {
		// TODO Auto-generated method stub
		setBorderColor(new Color(0x66, 0xCC, 0xFF));
		setLiveColor(Color.BLUE);
		setDeadColor(new Color(0xCC, 0xCC, 0xFF));
		setMenuName("Blue");
	}
}
