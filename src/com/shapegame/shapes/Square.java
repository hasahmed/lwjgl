package com.shapegame.shapes;

import com.shapegame.Color;

/*
 * Project : shapegame
 * Package : com.shapegame.shapes
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */


public class Square extends Shape{
    private int size;


    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Square(float x, float y, int size, Color color){
        super(x, y, size, size, color);
        this.size = size;
        this.shapeType = ShapeType.SQUARE;
    }
}
