package com.shapegame; /**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
public abstract class App {

    abstract int getWindowHeight();
    abstract int getWindowWidth();

    private long window; //window handle
    private GLHandler glHandler;

    private void teardown(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        //createWindow();
        //window creation
        Window w = new Window(this.getWindowWidth(), this.getWindowHeight());
        this.window = w.getWindow();

        //opengl instance creation
        this.glHandler = new GLHandler();

        //the loop where the opengl drawing happens
        loop();
        teardown();
    }

    private void loop() {
        float r, g, b;
        r = 1f;
        g = 0f;
        b = 0f;

        while ( !glfwWindowShouldClose(window) ) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            glfwPollEvents(); //for key handling
            //drawSquares(r, g, b);
            glHandler.drawSquares(r, g, b, 1f);
            glHandler.drawTriangles(0f, 1f, 0f, 0.5f);
            //drawTriangles();
            glfwSwapBuffers(window);
        }
    }
}
