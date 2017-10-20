package com.shapegame;

/**
 * Created by Hasan Y Ahmed on 10/18/17.
 */
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.lwjgl.opengl.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Window {
   private long window;

   Window(int width, int height){
      // Setup an error callback. The default implementation
      // will print the error message in System.err.
      GLFWErrorCallback.createPrint(System.err).set();

      // Initialize GLFW. Most GLFW functions will not work before doing this.
      if ( !glfwInit() )
         throw new IllegalStateException("Unable to initialize GLFW");

      // Configure GLFW
      glfwDefaultWindowHints(); // optional, the current window hints are already the default
      glfwWindowHint(GLFW_SAMPLES, 4); //antialiasing
      glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
      glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will not be resizable

      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

      // Create the window
      this.window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
      if ( this.window == NULL )
         throw new RuntimeException("Failed to create the GLFW window");

      // Setup a key callback. It will be called every time a key is pressed, repeated or released.
      glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
         if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      });

      // Get the thread stack and push a new frame
      try ( MemoryStack stack = stackPush() ) {
         IntBuffer pWidth = stack.mallocInt(1); // int*
         IntBuffer pHeight = stack.mallocInt(1); // int*

         // Get the window size passed to glfwCreateWindow
         glfwGetWindowSize(this.window, pWidth, pHeight);

         // Get the resolution of the primary monitor
         GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

         // Center the window
         glfwSetWindowPos(
                 this.window,
                 (vidmode.width() - pWidth.get(0)) / 2,
                 (vidmode.height() - pHeight.get(0)) / 2
         );
      } // the stack frame is popped automatically

      // Make the OpenGL context current
      glfwMakeContextCurrent(this.window);
      // Enable v-sync
      glfwSwapInterval(1);
      // Make the window visible
      glfwShowWindow(this.window);
   }

   public long getWindow() {
      return window;
   }
}
