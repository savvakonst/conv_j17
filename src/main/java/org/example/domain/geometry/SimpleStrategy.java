package org.example.domain.geometry;



public class SimpleStrategy implements PlacementStrategyIfs {


    @Override
    public Circle place(InletType inletType, Axis axes []) {
        // TODO Auto-generated method stub
        int y = 0 ;
        Axis axis = null;
        for(Axis i : axes){
            var s = i.getLeastSegment();
            if (s!=null){
                if (s.p1 <y || axis == null){
                    y = s.p1;
                    axis = i;
                }
            }
        }
        if (axis == null)
            return null;
        
        return new Circle(inletType, new Coordinate(axis.x ,y)) ;
    }
    
}
