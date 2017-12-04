package com.holub.clockoption;

public class Slow extends ClockOption{
	@Override
	public void setClassInfo() {
		// TODO Auto-generated method stub
		setName("Slow");
		setCycleMiliSecond(150);
		setType(ClockOption.SETCLOCK);
	}
}
