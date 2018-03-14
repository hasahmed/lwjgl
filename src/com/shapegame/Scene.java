package com.shapegame;

/*
 * Project : shapegame
 * Package : com.shapegame
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */

import com.shapegame.shapes.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Scene {
    private int INITIAL_ARRAY_SIZE = 1000;

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    private ArrayList<Shape> shapes; //array of shapes to render
//    GLUtil glUtilInstance;

    public Scene(){
        shapes = new ArrayList<>(INITIAL_ARRAY_SIZE);
//        glUtilInstance = glUtil;
    }

    public void addChild(Shape shape){
        shapes.add(shape);
    }
}
