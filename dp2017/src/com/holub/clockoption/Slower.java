package com.holub.clockoption;

public class Slower extends ClockOption{
	@Override
	public void setClassInfo() {
		// TODO Auto-generated method stub
		setName("Slower");
		setCycleMiliSecond(0);
		setType(ClockOption.SLOWER);
	}
}
