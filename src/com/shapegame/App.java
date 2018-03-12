package com.shapegame; /**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
public abstract class App {

    public abstract int getWindowHeight();
    public abstract int getWindowWidth();

    private long glfwWindowHandle; //window handle
    private GLHandler glHandler;

    private void teardown(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.glfwWindowHandle);
        glfwDestroyWindow(this.glfwWindowHandle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        //window creation
        Window w = new Window(this.getWindowWidth(), this.getWindowHeight());
        this.glfwWindowHandle = w.getWindowHandle();

        //opengl instance creation
        this.glHandler = new GLHandler(new GLUtil(this.getWindowWidth(), this.getWindowHeight()));

        //the loop where the opengl drawing happens
        loop();
        teardown();
    }

    private void loop() {
        float r, g, b;
        r = 1f;
        g = 0f;
        b = 0f;

        while ( !glfwWindowShouldClose(glfwWindowHandle) ) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            glfwPollEvents(); //for input handling
            //drawSquares(r, g, b);
            glHandler.drawSquares(r, g, b, 1f);
            glHandler.drawTriangles(0f, 1f, 0f, 0.5f);
            //drawTriangles();
            glfwSwapBuffers(glfwWindowHandle);
        }
    }
}
