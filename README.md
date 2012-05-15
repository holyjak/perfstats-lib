INTRO
Light-weight, minimalistic, low-overhead lib for collecting and publishing performance statistics in production systems.
Calls to the lib are usually injected via AOP.

Overview:
- code calls Statistics.recordMeasurement(String metricName, long duration [, unit]) 
- the measurement is added to a list of values to process and the call returns
- a background thread processes new measurement, updating aggregate values etc.
- the collected values are made available via publishers such as JmxPublisher and PeriodicalLogPublisher

USAGE

Statistics.recordMeasurement("myBizMethod", 60);
// => asynch. stored and updated count, avg, min, max, std.dev, ...
// Computation strategy: rolling window x all values

STATS

- ignore lowest and highest N values (random extreems far behind normal values)
- count avg, 10 min, 10 max, std.dev.
- running avg/dev or based on all values

ATTRS OF INTEREST

Count	attribute	The number of times this event has occurred.
ErrorCount	attribute	The number of times this event ended in error.
AverageTime	attribute	The average execution time, in the selected unit.
MaxTime	attribute	The maximum execution time, in the selected unit.
MinTime	attribute	The minimum execution time, in the selected unit.
StandardDeviation	attribute	The standard deviation from the mean, in the selected unit.
LastInvocation	attribute	The most recent date and time at which this event occurred.
Units	attribute	The unit of measure for max, min, average, and stddev. Valid values are seconds, milliseconds, and nanoseconds. Defaults to milliseconds if unspecified.
reset	operation	Reset statistics for this type of event.

