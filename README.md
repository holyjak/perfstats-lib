About perfstats-lib
===========

What it is
--------

Perfstats-lib is a light-weight, minimalistic, low-overhead library for collecting and publishing performance statistics 
in production systems, written in Java. Calls to the library can be performed manually but are expected to be injected 
automatically to the production code via AOP. (That's one of the reasons for keeping the public API minimal.)

*Beware*: Most of the stuff written below is a plan. To see what is actually implemented, check the History section at the end.

Vision:

- The production code calls measurement = PerfStats.startFor("myMethod"); ... measurement.stop() to record a duration
	of a method execution
- The measurement is added to a list of values to process and the call returns immediately
- A background thread processes new measurement, updating aggregate values etc.
- The collected values are made available via publishers such as JmxPublisher and PeriodicalLogPublisher

What it is not
-----------

Perfstats-lib isn't a profiler, a generic monitoring software (i.e. no memory, JVM, CPU, etc. stats), a full-featured performance
monitoring solution storing data to a DB, monitoring for multiple JVMs.

When to use
----------

If you need to collect performance statistics of (few) known methods in your production code
(i.e. using a profiler isn't feasible) and want to minimize the dependencies added to your production code.
It's also useful if you need to collect performance statistics only temporarily.

If you want performance monitoring to be an integral part of your application then you may prefer a more
feature-rich solution such as NewRelic or Stajistics, that support data persistence.

### Alternatives

- A profiler
- Perf4j
- Integration into generic monitoring solutions such as NewRelic, Hyperic, Zabbix, or Nagios
- Likely some more

Usage
====

```
OngoingMeasurement measurement = PerfStats.startFor("myBizMethod");
// myBizMethod executes ...
measurement.stop();
...
System.out.println(PerfStats.getMetric("myBizMethod").getMax());
System.out.println(PerfStats.getMetric("myBizMethod").reportAsCsv());
```

Stats
====

Planned/implemented
------------------

- Count, avg, 10 quickest, 10 slowest, mean, std.dev., min, max
- Perhaps we should ignore N (1-10 or a percent?) lowest and highest values so that mean and std. dev. aren't distorted too much
 by a random and very rare extreme (such as an operating taking 30 min while average is 10s).
- running avg/dev or based on all values

Aggregation strategies
------------------

1. Aggregation based on the complete history, i.e. all measurements collected (most accurate, unbounded memory growth)
2. Running aggregates - only keep an aggregation of the historical values and use that to compute approximate values of the  statistics
    (only approximate results, limited memory use; the preferred strategy)
3. Extremes-ignoring aggregation meta-strategy - ignore few smallest/largest measurements as described above

Statistics considered for inclusion
---------------------------

(Partly copied from a source I do not remember anymore, perhaps NewRelic.)

- Count	The number of times this event has occurred.
- ErrorCount	The number of times this event ended in error.
- AverageTime	The average execution time, in the selected unit.
- MaxTime	The maximum execution time, in the selected unit.
- MinTime	The minimum execution time, in the selected unit.
- StandardDeviation	The standard deviation from the mean, in the selected unit.
- LastInvocation	The most recent date and time at which this event occurred.
- Units	The unit of measure for max, min, average, and stddev. Valid values are seconds, milliseconds, and nanoseconds. Defaults to milliseconds if unspecified.
- The period when the logs were collected
- Number of calls during a period (transactions per period)

License
=====

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Future & History
==========

Future plans
----------

- Support for non-invasive injection of the PerfStats calls via AOP (likely Javassist or similar) at build- (and perhaps also run-) time
- Implementation of the Running Aggregates strategy
- More stats (primarily mean and std. dev.)
- Support for counters in addition to durations (e.g. to count the number of errors, possibly keeping last N of them)
- Add more publishers (PeriodicalMetaPublisher, ScheduledMetaPublisher, JavaUtilLoggingPublisher, JmxPublisher, SysoutPublisher, ...)
- That's it, folks. I told you it's minimalistic, haven't I? :-)

History
------

### Current limitations

- There is only one instance of PerfStats per JVM (you might prefer e.g. one per webapp)

### 1.0 (5/2012)

- Collect basic statistics (min, max, avg, count)
- Return all the collected stats as a comma-separated values (CSV) string
- Aggregation based on the complete history
- All the processing is done synchronously (but the aggregates are only computed on-demand)

Related projects
-------------

- [Perf4j](http://perf4j.codehaus.org/) - integrates tightly with a logging framework and its appenders for recording/aggregating stats; 
   some good stuff to copy :-) such as real-time graphs and JMX support
- [Stajistics](http://code.google.com/p/stajistics/wiki/Introduction) - a powerful monitoring and runtime performance statistics collection library
   intended for continuous use in production systems with support for persistence and historical comparisons
   
Closing words
-----------

Contribution and feedback are welcomed :-).
