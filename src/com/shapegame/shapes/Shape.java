package com.shapegame.shapes;
/*
 * Created by Hasan Y Ahmed on 10/23/17.
 */
import com.shapegame.Color;
import com.shapegame.Position;
import com.shapegame.Scene;

import java.awt.Point;

public abstract class Shape {
    int height, width;
    Position position;
    public ShapeType shapeType;
    Color color;

    public void setVerts(float[] verts) {
        this.verts = verts;
    }

    float[] verts;
    private Scene scene; //the scene the shape is a part of
    Shape(float x, float y, int width, int height,  Color color){
        this.color = color;
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
    }
    Shape(Position position, int width, int height, Color color){
        this.position = new Position(0, 0);
        this.color = color;
        this.position = position;
        this.width = width;
        this.height = height;
    }
    Shape(int width, int height) {
        this(new Position(0, 0), width, height, Color.BLACK);
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor(){
        return this.color;
    }
    public float[] getVerts(){
        return this.verts;
    }
}
