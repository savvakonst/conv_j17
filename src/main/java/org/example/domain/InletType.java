package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InletType {
    private final String inletName;
    private final int r;
    private int gap = 0;

    public int getQuasiRadius() {return gap+r;} 
}
