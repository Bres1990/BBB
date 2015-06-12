package com.example.Galeria2;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * A shape is a collection of points marked out by single stroke (lines) or several taps (figures).
 * @since 24-05-2015
 * @author Ala
 */
public class Shape {
    private ArrayList<PointF> points;
    private Path shape;
    private Paint myPaint;

    Shape(Paint paint){
        points = new ArrayList<PointF>();
        shape = new Path();
        myPaint = paint;
    }

    public void addPoint(float x, float y){

        if(points.isEmpty()){
            points.add(new PointF(x, y));
            shape.moveTo(x,y);
        }
        else{
            points.add(new PointF(x,y));
            PointF previous = points.get(points.size()-2);
            shape.quadTo(previous.x, previous.y, (previous.x+x)/2, (previous.y+y)/2);
        }

    }

    public int getSize(){
        return points.size();
    }

    public void removeLastPoint(){
        if(points.isEmpty()) return;
        points.remove(points.size() - 1);

        shape.reset();
        shape.moveTo(points.get(0).x, points.get(0).y);

        for(int i=1; i<points.size(); i++){
            PointF previous = points.get(i-1);
            shape.quadTo(previous.x, previous.y, (previous.x+points.get(i).x)/2, (previous.y+points.get(i).y)/2);
        }
    }

    public Path getPath(){
        return shape;
    }

    public Paint getPaint(){
        return myPaint;
    }

    public void setPath(Path path){
        shape.set(path);
    }
}
