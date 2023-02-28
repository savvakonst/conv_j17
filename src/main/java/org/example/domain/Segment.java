package org.example.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Segment {
	private int y1;
	private int y2;
}
