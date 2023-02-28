package org.example.domain.geometry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinate {
  public final int x;
  public final int y;

  @Override
    public boolean equals(Object o) {
      
        if (!(o instanceof Coordinate)) {
          return false;
        }
        Coordinate c = (Coordinate)o;
        return x==c.x && y==c.y;
    }
}
