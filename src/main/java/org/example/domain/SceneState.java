package org.example.domain;


import org.example.domain.Line.LType;

import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;




import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;






@Data
@AllArgsConstructor
public class SceneState {
    private final LinkedList<Line> borders;
    private List<Circle> circles;

    // integer should reflect uniqueness of the inlet (maybe it is quasi radius)
    private final List<Integer> axesCoordinates; 

    private HashMap<Integer, Axis[]> axes; 
    
    final Line sceneRectangle;

    public SceneState(List<Integer> axesCoordinates_, LinkedList<Line> borders_){

        // need to throw error if there are no borders

        axesCoordinates = axesCoordinates_;
        axes = new HashMap<Integer,Axis[]>();


        borders = borders_;
        
        { 
            var b = borders.get(0);
            int x_max=b.x.p2, x_min =b.x.p1, y_max=b.y.p2,y_min=b.y.p1;
            for(var i : borders){
                x_max =  i.x.p2 > x_max ? i.x.p2 : x_max;
                x_min =  i.x.p1 < x_min ? i.x.p1 : x_min;
                y_max =  i.y.p2 > y_max ? i.y.p2 : y_max;
                y_min =  i.y.p1 < y_min ? i.y.p1 : y_min;
            }
            sceneRectangle = new Line(x_min,y_min,x_max,y_max);
        }   
        
    }

    private  Axis[] addAxis(InletType inletType){
        int numberOfAxles = axesCoordinates.size();
        Axis[] axleSet = new Axis[numberOfAxles];

        for (int i=0; i<numberOfAxles; i++){
            
            Axis axis = new Axis(axesCoordinates.get(i),sceneRectangle.y);
            for (var border: borders)
                axis.addLineRestriction(inletType.getQuasiRadius(), border);
            
            // removing outside lines
            
            var itr = axis.getSegments().listIterator();
            while (itr.hasNext()){
                var segment = itr.next();
                var y = segment.getP1();
                var x = axis.x;
                int intersection_counter = 0;
                for (var border: borders)
                    if (border.getType()==Line.LType.VERTICAL && border.getXPos() < x)
                        if ( border.y.contains(y)) intersection_counter++;
                
                if((intersection_counter&1) == 0)
                    itr.remove();

            }

            axleSet[i] = axis;            
        }
           
        return axleSet;
    }

    public void addCircle(InletType inletType){

        var r = inletType.getQuasiRadius();
        
        var axleSet = axes.get(r);
        
        if (axleSet == null) 
            axleSet = addAxis(inletType);

        axes.put(r, axleSet);
        //circles.add(circle);
    }

    // returns the diagonal of the scene rectangle. Diagonal from top left to right bottom corner.
    public void getSceneRectangle(Circle circle){
        
    }

}
