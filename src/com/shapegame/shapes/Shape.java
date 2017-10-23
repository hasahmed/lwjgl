package com.shapegame.shapes;
/**
 * Created by Hasan Y Ahmed on 10/23/17.
 */
import com.shapegame.Color;
import java.awt.Point;

public abstract class Shape {
    int height, width;
    Point position;
    String shapeName;
    Color color;
    float[] verts;
    Shape(int x, int y, int width, int height, float[] verts, Color color){
        this.verts = verts;
        this.color = color;
        this.position = new Point(x, y);
        this.width = width;
        this.height = height;
    }
    Shape(Point position, int width, int height, float[] verts, Color color){
        this.verts = verts;
        this.color = color;
        this.position = position;
        this.width = width;
        this.height = height;
    }
    Shape(){

    }
}
