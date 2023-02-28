package org.example.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Circle {
    private Coordinate p;
    private int r;
    private int gap = 0;

    public int getQuasiRadius() {return gap+r;} 
}
