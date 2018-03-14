package com.shapegame;

/*
 * Project : shapegame
 * Package : com.shapegame
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */
public class Position {
    private float x;
    private float y;
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public float getX(){
        return this.x;
    }

    public float getY() {
        return y;
    }
}
