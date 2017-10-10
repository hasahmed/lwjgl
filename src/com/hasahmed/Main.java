package com.hasahmed; /**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import org.lwjgl.glfw.*;
import org.lwjgl.system.*;
import org.lwjgl.opengl.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {


    private long window; //window handle
    private int progHandle; //the handle to the shader program
    private int vertexBuffer;


    private float verts[] = {
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0f, 0.5f, 0f
    };


    private void teardown(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void run() {
        createWindow();
        init();
        loop();
        teardown();

    }

    private void createWindow(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_SAMPLES, 4); // the window will stay hidden after creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will not be resizable

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        window = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop

            if (key == GLFW_KEY_A){
                System.out.println("A");
            }
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }

    private void init() {

        //call first
        GL.createCapabilities();
        GL11.glClearColor(0f, 1f, 1f, 1f);


        //THE VAO
        int vertextArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertextArrayID);


        //DRAWING OUR TRIANGLE
        vertexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW);




        //<<<<<<<<<<<<SHADERS>>>>>>>>>>>>>>
        String[] shaderBufferArr = GLUtil.readinShaders("test.frag", "test.vert"); //read shaders into string array
        int vertShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        int fragShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragShaderID, shaderBufferArr[0]);
        GL20.glShaderSource(vertShaderID, shaderBufferArr[1]);

        GL20.glCompileShader(fragShaderID);
        GL20.glCompileShader(vertShaderID);


        progHandle = GL20.glCreateProgram();
        GL20.glAttachShader(progHandle, fragShaderID);
        GL20.glAttachShader(progHandle, vertShaderID);
        GL20.glLinkProgram(progHandle);

        GL20.glDetachShader(progHandle, vertShaderID);
        GL20.glDetachShader(progHandle, fragShaderID);


        GL20.glDeleteShader(vertShaderID);
        GL20.glDeleteShader(fragShaderID);
    }

    private void loop() {
        while ( !glfwWindowShouldClose(window) ) {
            glfwPollEvents(); //for key handling
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL20.glUseProgram(progHandle);

            GL20.glEnableVertexAttribArray(0);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);

            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3); //breaks because no shaders
            GL20.glDisableVertexAttribArray(0);
            glfwSwapBuffers(window);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
