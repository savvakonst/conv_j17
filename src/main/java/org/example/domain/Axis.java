package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Axis {
	private int x;
	private LinkedList<Segment> segments;

	public void modifySegmentsByAnotherSegment(Segment a) {
		for (int i = 0; i < segments.size(); i++) {
			Segment currentEl = segments.remove(i);
			List<Segment> result = calculateDiff(currentEl, a);
			if (result.size() > 0) {
				segments.addAll(i, result);
			}
		}
	}

	public static List<Segment> calculateDiff(Segment a, Segment b) {
		if (b.y1 <= a.y1) {
			if (a.y2 <= b.y2) // it needs only once
				return new ArrayList<>(Collections.emptyList());
			if (b.y2 <= a.y1)
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(b.y2, a.y2)));
		}
		if (a.y2 <= b.y2) {
			if (a.y2 <= b.y1)
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(a.y1, b.y1)));
		}
		return new ArrayList<>(List.of(
				new Segment(a.y1, b.y1),
				new Segment(b.y2, a.y1)
		));
	}

	public static List<Segment> findSegment(int r, int d, int y0) {
		if (r > d) return Collections.emptyList();

		int delta =  (int) Math.ceil(Math.sqrt(Math.pow(r, 2) - Math.pow(d, 2)));

		int y1 = y0 - delta;
		int y2 = y0 + delta;

		return List.of(new Segment(y1, y2));
	}

	public static List<Segment> findSegment(int y1, int y2, int d, int r) {
		if (r > d) return Collections.emptyList();
		int delta =  (int) Math.ceil(Math.sqrt(Math.pow(r, 2) - Math.pow(d, 2)));
		return List.of(new Segment((y1 - delta), (y2 + delta)));
	}

	//	public static List<Segment> calculateDiff(Segment seg1, Segment seg2) {
//		int x1 = seg1.y1;
//		int x2 = seg1.y2;
//		int y1 = seg2.y1;
//		int y2 = seg2.y2;
//
//		if (x1 == y1) {
//			if (x2 == y2) return Collections.singletonList(new Segment(y2, x2));
//			else return Collections.emptyList();
//		}
//		if (x1 < y1) {
//			if (x2 == y2) return Collections.singletonList(new Segment(x1, y1));
//			if (x2 > y2) return List.of(new Segment(x1, y1), new Segment(y2, x2));
//			else return Collections.singletonList(new Segment(x1, y1));
//		}
//		if (x1 > y1) {
//			if (x2 > y2) return Collections.singletonList(new Segment(y2, x2));
//			else return Collections.emptyList();
//		}
//		throw new RuntimeException("Не возможно произвести расчеты");
//	}
}