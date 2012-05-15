package net.jakubholy.jeeutils.perfstats;

import static net.jakubholy.jeeutils.perfstats.PerfStats.getMetric;
import static net.jakubholy.jeeutils.perfstats.PerfStats.startFor;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PerfStatsWithControllableClockTest {

	private ControllableClock clock;

	@Before
	public void setUp() {
		PerfStats.reset();
		clock = new ControllableClock();
		PerfStats.setClock(clock);
	}

	@Test
	public void testAll() {
		// record 3 measurements
		OngoingMeasurement first = startFor("myMetode");
		clock.addSeconds(1);
		first.stop();

		OngoingMeasurement second = startFor("myMetode");
		clock.addSeconds(3);
		second.stop();

		OngoingMeasurement third = startFor("myMetode");
		clock.addSeconds(5);
		third.stop();

		// get count, avg, max, min
		Metric metric = getMetric("myMetode");
		assertEquals(3, metric.getCount());
		assertEquals(3000, metric.getAverage());
		assertEquals(5000, metric.getMax());
		assertEquals(1000, metric.getMin());

		// report metrics recorded as CSV: methodName, count, max, min, avg
		String report = PerfStats.reportAsCsv();
		assertThat(report , containsString("myMetode,3,5000,1000,3000\n"));
		assertThat(report , containsString("metric,count,max,min,avg\n"));
	}

	@Test
	public void one_measurement_should_be_counted() {
		OngoingMeasurement measurement = startFor("myMetode");
		measurement.stop();

		assertEquals(1, getMetric("myMetode").getCount());
	}

	@Test
	public void two_measurements_should_be_counted() {
		startFor("myMetode").stop();
		startFor("myMetode").stop();

		assertEquals(2, getMetric("myMetode").getCount());
	}

	@Test
	public void two_different_metrics_should_be_counted_separately() {
		startFor("oneMethod").stop();
		startFor("anotherMethod").stop();

		assertEquals(1, getMetric("oneMethod").getCount());
		assertEquals(1, getMetric("anotherMethod").getCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void methodName_null_should_throw_exception() throws Exception {
		startFor(null);
	}

	@Test
	public void initial_average_should_be_zero() throws Exception {
		startFor("myMetode").stop();

		assertEquals(0, getMetric("myMetode").getAverage());
	}

	@Test
	public void should_record_average() throws Exception {
		OngoingMeasurement first = startFor("myMetode");
		clock.addSeconds(1);
		first.stop();

		OngoingMeasurement second = startFor("myMetode");
		clock.addSeconds(3);
		second.stop();

		assertEquals(2000, getMetric("myMetode").getAverage());
	}

	/*
	 * TODO TESTS
	 * - OngoingMeasurement allows stop() to be called only once
	 */

}
