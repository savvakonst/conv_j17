package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Segment {
	final int p1;
	final int p2;

	// create constructor which will ensure that p1 is less or equal p2
	
	public boolean contains(int a){
		return  p1<=a && a <= p2;
	}
}
