package org.example.domain;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class SegmentUtils {
	public static List<Segment> calculateDiff(Segment a, Segment b) {
		if (b.getY1() <= a.getY1()) {
			if (a.getY2() <= b.getY2()) // it needs only once
				return new ArrayList<>(Collections.emptyList());
			if (b.getY2() <= a.getY1())
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(b.getY2(), a.getY2())));
		}
		if (a.getY2() <= b.getY2()) {
			if (a.getY2() <= b.getY1())
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(a.getY1(), b.getY1())));
		}
		return new ArrayList<>(List.of(
				new Segment(a.getY1(), b.getY1()),
				new Segment(b.getY2(), a.getY1())
		));
	}

	public static List<Segment> findSegment(int y0, int leg, int hypotenuse) {
		if (hypotenuse > leg) return Collections.emptyList();
		return Collections.singletonList(
				Segment.builder()
						.y1(findY(y0, leg, hypotenuse, true))
						.y2(findY(y0, leg, hypotenuse, false))
						.build()
		);
	}

	public static List<Segment> findSegment(int y1, int y2, int leg, int hypotenuse) {
		if (hypotenuse > leg) return Collections.emptyList();
		return Collections.singletonList(
				Segment.builder()
				.y1(findY(y1, leg, hypotenuse, true))
				.y2(findY(y2, leg, hypotenuse, false))
				.build()
		);
	}

	/**
	 * @param upOrDown true - вверх по оси Y, false - вниз по оси Y. Отсчет по оси Y идет сверху вниз.
	 */
	public static int findY(int y0, int leg, int hypotenuse, boolean upOrDown) {
		int delta =  (int) Math.ceil(Math.sqrt(Math.pow(hypotenuse, 2) - Math.pow(leg, 2)));
		return upOrDown ? y0 - delta : y0 + delta;
	}

//	    public static List<Segment> calculateDiff(Segment seg1, Segment seg2) {
//		int x1 = seg1.getY1();
//		int x2 = seg1.getY2();
//		int y1 = seg2.getY1();
//		int y2 = seg2.getY2();
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
