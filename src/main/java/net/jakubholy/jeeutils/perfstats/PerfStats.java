package net.jakubholy.jeeutils.perfstats;

import java.util.HashMap;
import java.util.Map;

public class PerfStats {

	private static final Map<String, Metric> metricByMethod = new HashMap<String, Metric>();

	private static Clock clock = new SystemClock();

	public static OngoingMeasurement startFor(String methodName) {
		if (methodName == null) {
			throw new IllegalArgumentException("methodName is required");
		}
		if(!metricByMethod.containsKey(methodName)) {
			metricByMethod.put(methodName, new Metric(methodName));
		}

		Metric metric = metricByMethod.get(methodName);
		metric.incrementCount();

		OngoingMeasurement ongoingMeasurement = new OngoingMeasurement(clock, metric);
		return ongoingMeasurement;
	}

	public static Metric getMetric(String methodName) {
		return metricByMethod.get(methodName);
	}

	public static void reset() {
		metricByMethod.clear();
	}

	static void setClock(Clock clock) {
		PerfStats.clock = clock;
	}

	public static String reportAsCsv() {
		StringBuilder csv = new StringBuilder("metric,count,max,min,avg\n");
		for (Metric metric : metricByMethod.values()) {
			csv.append(metric.toCsv()).append('\n');
		}
		return csv.toString();
	}
}
