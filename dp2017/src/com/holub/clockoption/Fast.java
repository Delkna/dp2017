package com.holub.clockoption;

public class Fast extends ClockOption {
	@Override
	public void setClassInfo() {
		// TODO Auto-generated method stub
		setName("Fast");
		setCycleMiliSecond(30);
		setType(ClockOption.SETCLOCK);
	}
}
