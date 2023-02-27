package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Axis {
	private int x;
	private List<Segment> segments;

	public static List<Segment> calculateDiff(Segment seg1, Segment seg2) {
		if (seg1.getY1() < seg2.getY1() && seg2.getY2() < seg1.getY2()) {
			return new ArrayList<>(List.of(
					new Segment(seg1.getY1(), seg2.getY1()),
					new Segment(seg2.getY2(), seg1.getY1())
			));
		}
		if (seg1.getY2() > seg2.getY1()) {
			return new ArrayList<>(Collections.singletonList(seg1));
		}
		if (seg1.getY1() < seg2.getY1() && seg2.getY2() > seg1.getY1()) {
			return new ArrayList<>(Collections.singletonList(new Segment(seg1.getY1(), seg2.getY1())));
		}
		if (seg1.getY1() > seg2.getY1() && seg1.getY2() < seg2.getY2()) {
			return new ArrayList<>(Collections.emptyList());
		}
		throw new RuntimeException("Не возможно произвести расчеты");
	}
}