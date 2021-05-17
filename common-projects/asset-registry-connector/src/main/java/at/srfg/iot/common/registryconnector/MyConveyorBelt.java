package at.srfg.iot.common.registryconnector;

public class MyConveyorBelt {
	
	private long serverTime;
	private double distance = 0.0d;
	private boolean moving = false;
	private String state;
	
	public void switchBusyLight(boolean busy) {
		this.moving = busy;
	}
	public void moveBelt(String direction, float distance) {
		// opcuamanager.WriteValue(Direction, distance)
		this.distance += this.distance;
		this.state = direction;
	}
	
	public long getServerTime() {
		return System.currentTimeMillis();
	}
	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
