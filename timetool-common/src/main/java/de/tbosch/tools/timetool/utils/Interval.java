package de.tbosch.tools.timetool.utils;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class Interval {

	private final Temporal start;

	private final Temporal end;

	public Interval(Temporal start, Temporal end) {
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return (LocalDateTime) start;
	}

	public LocalDateTime getEnd() {
		return (LocalDateTime) end;
	}

}
