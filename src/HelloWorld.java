/**
 * Created by Hasan Y Ahmed on 10/7/17.
 */

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.IOException;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

    static String readFile(String path, Charset encoding){
        Path currentDir = Paths.get("shaders");
        currentDir = currentDir.toAbsolutePath();
        Path p = Paths.get(currentDir.toString(), path);
        try {
            byte[] encoded = Files.readAllBytes(p);
            return new String(encoded, encoding);
        } catch (IOException e){
            System.out.println("Something went wrong with the reading of the shader: " + e.getMessage());
        }
        return null;
    }

    float verts[] = {
                0f, 0f, 0f,
                -1f, -1, 0f,
                1f, 1f, 0f
    };

    // The window handle
    private long window;

    public void run() {
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    private int[] readinShaders(){
        String fragmentShaderBuffer = readFile("test.frag", Charset.defaultCharset());
        String vertexShaderBuffer = readFile("test.vert", Charset.defaultCharset());
        int fragShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        int vertShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        if (fragmentShaderBuffer != null && vertexShaderBuffer != null){
            GL20.glShaderSource(fragShader, fragmentShaderBuffer);
            GL20.glShaderSource(vertShader, vertexShaderBuffer);
        } else{
            System.out.println("Something went wrong with the reading in of the shaders");
        }
        int[] shaderArr = {fragShader, vertShader};
        return shaderArr;
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will not be resizable

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
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

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        GL11.glClearColor(0f, 0f, 1f, 0f);

        int[] shaderArr = readinShaders();
        int fragShader = shaderArr[0];
        int vertShader = shaderArr[1];


        GL20.glCompileShader(fragShader);
        GL20.glCompileShader(vertShader);

        int progHandle = GL20.glCreateProgram();
        GL20.glAttachShader(progHandle, fragShader);
        GL20.glAttachShader(progHandle, vertShader);
        GL20.glLinkProgram(progHandle);
        GL20.glUseProgram(progHandle);


        int buffer = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verts, GL15.GL_STATIC_DRAW);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0l);


        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glfwPollEvents();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

            glfwSwapBuffers(window);

        }
    }

    public static void main(String[] args) {
        new HelloWorld().run();
    }

}
