package com.holub.clockoption;

public abstract class ClockOption {
	public static final int SETCLOCK = 0;
	public static final int HALT = 1;
	public static final int TICK = 2;
	public static final int SLOWER = 3;
	public static final int FASTER = 4;
	
	private String name = "";
	private int cycleMiliSecond = 0;
	private int type;
	
	public ClockOption() {
		setClassInfo();
	}
	
	public abstract void setClassInfo();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCycleMiliSecond(int cycleMiliSecond) {
		this.cycleMiliSecond = cycleMiliSecond;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCycleMiliSecond() {
		return cycleMiliSecond;
	}
	
	public int getType() {
		return type;
	}
}
