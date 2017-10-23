package com.shapegame.shapes;
/**
 * Created by Hasan Y Ahmed on 10/23/17.
 */
public class VertexManipulator {
    private float vertPixelStep, horPixelStep;
    VertexManipulator(int screenWidth, int screenHeight){
        this.horPixelStep = 2f / (float)screenWidth;
        this.vertPixelStep = 2f / (float)screenHeight;
    }

    void generateRectangle(int screenx, int screeny, Rectangle rect) {
        float x = -1f + ((float)screenx * this.horPixelStep);
        float y = 1f - ((float)screeny * this.vertPixelStep);
        float xsize = (float)rect.width * this.horPixelStep;
        float ysize = (float)rect.height * this.vertPixelStep;
        float squareVerts[] = {
                // triangle 1
                x,              y - ysize,   0f, //lower left,
                x + xsize,      y - ysize,   0f, //lower right
                x,              y,           0f, // top left


                // triangle 2
                x,              y,           0f, // top left
                x + xsize,      y - ysize,   0f, //lower right
                x + xsize,      y,           0f //lower right
        };
        rect.verts = squareVerts;
    }
    void translate(int x, int y, Shape shape){
        float realx = x * horPixelStep;
        float realy = y * vertPixelStep;
        for(int i = 0; i < shape.verts.length; i += 3){
            shape.verts[i] += realx;
            shape.verts[i + 1] += realy;
        }
    }
}
