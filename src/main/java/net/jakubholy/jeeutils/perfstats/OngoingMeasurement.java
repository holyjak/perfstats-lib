package net.jakubholy.jeeutils.perfstats;

public class OngoingMeasurement {

	private final Clock clock;
	private final Metric metric;
	private long startTime;

	public OngoingMeasurement(Clock clock, Metric metric) {
		this.clock = clock;
		this.metric = metric;
		startTime = clock.getCurrentTimeMillis();
	}

	public void stop() {
		metric.recordMeasurement(clock.getCurrentTimeMillis() - startTime);
	}

}
