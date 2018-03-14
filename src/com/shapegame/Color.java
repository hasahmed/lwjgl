package com.shapegame;

/**
 * Created by Hasan Y Ahmed on 10/23/17.
 */
public class Color {
    public float r, g, b, a;
    public Color(float r, float g, float b, float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    public Color(float r, float g, float b){
        this(r, g, b, 1f);
    }

    float[] getColorArray(){
        return new float[]{r, g, b, a};
    }


    //color constants
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color CLEAR = new Color(0, 0, 0, 0);
}
