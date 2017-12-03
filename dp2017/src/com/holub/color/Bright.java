package com.holub.color;

import java.awt.Color;

public class Bright extends ColorSet{
	@Override
	public void setColorSetInfo() {
		// TODO Auto-generated method stub
		setBorderColor(new Color(0xDD, 0xDD, 0xDD));
		setLiveColor(Color.BLACK);
		setDeadColor(Color.WHITE);
		setMenuName("Bright");
	}
}
