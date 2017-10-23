package com.shapegame; /**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
public abstract class App {


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

    public void run() {
        createWindow();
        this.glHandler = new GLHandler();
        loop();
        teardown();
    }


    private void createWindow(){
        Window w = new Window(800, 600);
        this.window = w.getWindow();
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
            glHandler.drawSquares(r, g, b, 0.5f);
            glHandler.drawTriangles(0f, 1f, 0f, 0.5f);
            //drawTriangles();
            glfwSwapBuffers(window);
        }
    }
}
