package org.example.domain;



public class Line {

    public enum LType
    {
        VERTICAL,
        HORIZONTAL,
        OBLIQUE
    }


    private final LType t ; 
    final Segment x;
    final Segment y;

    public Line(int x1, int y1, int x2, int y2){

        x = x1<=x2? new Segment(x1,x2):new Segment(x2,x1);
        y = y1<=y2? new Segment(y1,y2):new Segment(y2,y1);

        if (x1==x2)
            // if (y1==y2)
            // it might be better to throw an error if y1==y1, for preventing situations when line becomes a point
            t = LType.VERTICAL;
        else if (y1==y2)
            t = LType.HORIZONTAL;
        else 
            t = LType.OBLIQUE;

    }
    
    public Segment getXProjection() {return x;}
    public Segment getYProjection() {return y;}

    public int getXPos(){ return x.getP1();}
    public int getYPos(){ return y.getP1();}

    public Coordinate getC1(){ return new Coordinate(x.p1,y.p1);}
    public Coordinate getC2(){ return new Coordinate(x.p2,y.p2);}

    public LType getType(){ return t;}

}
