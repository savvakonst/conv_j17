package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.*;

@Data
@AllArgsConstructor
public class Axis {

	final int x;
	private List<Segment> segments;

	Axis(int x_,Segment defaultSegment ){
		
		x=x_;

		//TODO deternine which is defenitly max and min 
		segments.add(defaultSegment);
	}

	public static List<Segment> calculateDiff(Segment a, Segment b) {
		if (b.getP1() <= a.getP1()) {
			if (a.getP2() <= b.getP2()) // it needs only once
				return new ArrayList<>(Collections.emptyList());

			if (b.getP2() <= a.getP1())
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(b.getP2(), a.getP2())));
		}
		if (a.getP2() <= b.getP2()) {
			if (a.getP2() <= b.getP1())
				return Collections.singletonList(a);
			else
				return new ArrayList<>(Collections.singletonList(new Segment(a.getP1(), b.getP1())));
		}
		return new ArrayList<>(List.of(
				new Segment(a.getP1(), b.getP1()),
				new Segment(b.getP2(), a.getP1())
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
}