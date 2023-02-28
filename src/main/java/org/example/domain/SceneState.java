package org.example.domain;

import java.util.List;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;






@Data
@AllArgsConstructor
public class SceneState {
    private final List<Line> borders;
    private List<Circle> circles;

    // integer should reflect uniqueness of the inlet (maybe it is quasi radius)
    private final List<Integer> axesCoordinates; 
    private HashMap<Integer, Axis[]> axes; 
    

    final Line sceneRectangle;


     
    public SceneState(List<Integer> axesCoordinates_, List<Line> borders_){

        // need to throw error if there are no borders
        axesCoordinates = axesCoordinates_;
        axes = new HashMap<Integer,Axis[]>();

        borders_.sort(
            (a,b)->( 
                (a.getXPos() == b.getXPos()) ? 
                (a.getYPos() - b.getYPos())  : 
                (a.getXPos() - b.getXPos())
            ));


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

    public  Axis[] addAxis(Circle circle){
        int numberOfAxles = axesCoordinates.size();
        Axis[] axleSet = new Axis[numberOfAxles];

        for (int i=0; i<numberOfAxles; i++)
            axleSet[i] = new Axis(axesCoordinates.get(i),sceneRectangle.y);
            
        return axleSet;
    }

    public void addCircle(Circle circle){

        var r = circle.getQuasiRadius();
        var axleSet = axes.get(r);
        if (axleSet != null) 
            axleSet = addAxis(circle);
        
        circles.add(circle);
    }

    // returns the diagonal of the scene rectangle. Diagonal from top left to right bottom corner.
    public void getSceneRectangle(Circle circle){
        
    }

}
