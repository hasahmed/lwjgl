package com.shapegame; /**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import com.shapegame.shapes.Shape;
import com.shapegame.shapes.Square;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
public abstract class App {

    public abstract int getWindowHeight();
    public abstract int getWindowWidth();


    private long glfwWindowHandle; //window handle
    private GLHandler glHandler;
    private Scene currentScene;
    private GLUtil glUtil;


    public App() {
        this.currentScene = new Scene();

    }

    private void teardown(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.glfwWindowHandle);
        glfwDestroyWindow(this.glfwWindowHandle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public Scene getScene(){
        return this.currentScene;
    }

    public void run(){
        //window creation
        Window w = new Window(this.getWindowWidth(), this.getWindowHeight());
        this.glfwWindowHandle = w.getWindowHandle();
        this.glUtil = new GLUtil(this.getWindowWidth(), this.getWindowHeight());

        //opengl instance creation
        this.glHandler = new GLHandler(glUtil);

        for (Shape shape : currentScene.getShapes()) {
            shape.setVerts(glUtil.makeVerts(shape)); // this makes the vertexes. Uses screen size to determine pixel
        }

        //the loop where the opengl drawing happens
        loop();
        teardown();
    }

    private void loop() {
        float r, g, b;
        r = 1f; //tmp
        g = 0f; //tmp
        b = 0f; //tmp

        while ( !glfwWindowShouldClose(glfwWindowHandle) ) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            glfwPollEvents(); //for input handling
            //drawSquares(r, g, b);
            for (Shape shape : currentScene.getShapes()) {
                glHandler.drawShape(shape); //add render shape on gpu
            }
//            glHandler.drawSquares(r, g, b, 1f); //tmp
//            glHandler.drawTriangles(0f, 1f, 0f, 0.5f); //tmp
            //drawTriangles();
            glfwSwapBuffers(glfwWindowHandle);
        }
    }
}
