package org.example.domain.geometry;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Circle {
    private final InletType inletType; 
    private final Coordinate p;

    public int getQuasiRadius() {return inletType.getQuasiRadius();} 
}
