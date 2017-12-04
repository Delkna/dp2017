package com.holub.clockoption;

public class Halt extends ClockOption{
	@Override
	public void setClassInfo() {
		// TODO Auto-generated method stub
		setName("Halt");
		setCycleMiliSecond(0);
		setType(ClockOption.HALT);
	}
}
