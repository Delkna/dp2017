package com.holub.clockoption;

public class Tick extends ClockOption{
	@Override
	public void setClassInfo() {
		// TODO Auto-generated method stub
		setName("Tick");
		setCycleMiliSecond(0);
		setType(ClockOption.TICK);
	}
}
