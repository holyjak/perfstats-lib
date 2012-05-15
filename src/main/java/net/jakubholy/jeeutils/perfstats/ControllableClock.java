package net.jakubholy.jeeutils.perfstats;

public class ControllableClock implements Clock {

	private long time;

	public void addSeconds(int i) {
		time += i*1000;
	}

	public long getCurrentTimeMillis() {
		return time;
	}

}
