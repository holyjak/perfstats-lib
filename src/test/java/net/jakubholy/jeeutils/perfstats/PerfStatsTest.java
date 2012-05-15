package net.jakubholy.jeeutils.perfstats;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

import org.junit.Test;


public class PerfStatsTest {

	@Test
	public void should_measure_time() throws Exception {
		final long beforeStartMs = System.currentTimeMillis();
		OngoingMeasurement measurement = PerfStats.startFor("anotherMethod");
		Thread.sleep(10);
		measurement.stop();
		final long afterStartMs = System.currentTimeMillis();

		long measuredDuration = PerfStats.getMetric("anotherMethod").getMax();
		assertThat(measuredDuration, is(greaterThanOrEqualTo(1l)));
		assertThat(measuredDuration, is(lessThanOrEqualTo(afterStartMs - beforeStartMs)));
	}

}
