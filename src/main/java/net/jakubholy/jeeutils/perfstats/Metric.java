package net.jakubholy.jeeutils.perfstats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Metric {

	private long count;
	private List<Long> measurements =  new ArrayList<Long>();
	private final String name;

	public Metric(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void incrementCount() {
		++count;
	}

	public long getAverage() {
		long total = 0;

		for (Long measurement : measurements) {
			total += measurement;
		}

		return total / measurements.size();
	}

	void recordMeasurement(long timeUsed) {
		measurements.add(timeUsed);
	}

	public long getMax() {
		return Collections.max(measurements);
	}

	public long getMin() {
		return Collections.min(measurements);
	}

	public String toCsv() {
		return name + ',' + count + ',' + getMax() + ',' + getMin() + ',' + getAverage();
	}

}
