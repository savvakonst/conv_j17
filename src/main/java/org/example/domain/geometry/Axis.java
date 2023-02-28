package org.example.domain.geometry;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.*;

@Data
@AllArgsConstructor
public class Axis {

	final int x;
	private LinkedList<Segment> segments;

	Axis(int x_, Segment defaultSegment ){
		x=x_;
		//TODO deternine which is defenitly max and min 
		segments = new  LinkedList<Segment>();
		segments.add(defaultSegment);
	}

	public void addCircleRestriction(int x_, int y, int radius){
		var segment = calcSegment(radius, Math.abs(x_ - x),y);
		if( segment!=null){
			modifySegmentsByAnotherSegment(segment);
		}
	}
	
	public void addCircleRestriction(Coordinate p, int radius){
		var segment = calcSegment(radius, Math.abs(p.x - x),p.y);
		if( segment!=null){
			modifySegmentsByAnotherSegment(segment);
		}
	}


	public void addLineRestriction(int radius, Line line){
		//System.out.println("("+line.x.p1 + ","+line.x.p2 +")" );
		switch (line.getType()) {
			case VERTICAL:{
				var segment = calcSegment(line.y.p1,line.y.p2,Math.abs(x-line.getXPos()),radius);
				if( segment!=null)
					modifySegmentsByAnotherSegment(segment);
				break;	
			}
			case HORIZONTAL:{
				if (line.x.contains(x)){
					modifySegmentsByAnotherSegment(new Segment(line.getYPos()-radius,line.getYPos()+ radius));
				}
				else {
					addCircleRestriction(line.getC1(),radius);
					addCircleRestriction(line.getC2(),radius);
					//addCircleRestriction(line.x.p1 ,line.y.p1, radius);
					//addCircleRestriction(line.x.p1 ,line.y.p2, radius);
				} 
				break;  
			}
			default:
				// TODO throw an arror since OBLIQUE borders is not supported yet 
				break;
		}
	}


	public void modifySegmentsByAnotherSegment(Segment a) {
		for (int i = 0; i < segments.size(); i++) {
			Segment currentEl = segments.removeFirst();//remove(0);
			List<Segment> result = calculateDiff(currentEl, a);
			segments.addAll( result);
		}
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
				new Segment(b.getP2(), a.getP2())
		));
	}


	public static Segment calcSegment(int r, int d, int y0) {
		if (r < d) return null;
		
		if (d==0) return new Segment(y0-r, y0+r);

		int delta =  (int) Math.ceil(Math.sqrt(Math.pow(r, 2) - Math.pow(d, 2)));

		return new Segment(y0 - delta, y0 + delta);
	}

	public static Segment calcSegment(int y1, int y2, int d, int r) {
		if (r < d) return null;
		int delta =  (int) Math.ceil(Math.sqrt(Math.pow(r, 2) - Math.pow(d, 2)));
		return new Segment((y1 - delta), (y2 + delta));
	}





	public Segment getLeastSegment(){
		if (segments.isEmpty())
			return null;

		return segments.getFirst(); 
	}
}