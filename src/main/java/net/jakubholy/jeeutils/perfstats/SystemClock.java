package net.jakubholy.jeeutils.perfstats;

public class SystemClock implements Clock {

	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
}
