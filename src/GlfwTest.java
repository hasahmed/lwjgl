/**
 * Created by Hasan Y Ahmed on 10/7/17.
 */
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL21.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GlfwTest {
    GLFWErrorCallback errorCallback;
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
    private void loop(){
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color

        java.util.Random randy = new java.util.Random();

        float r = randy.nextFloat() % 255;
        float g = randy.nextFloat() % 255;
        float b = randy.nextFloat() % 255;

        //System.out.printf("%f, %f, %f", r, g, b);
        glClearColor(r, g, b, 0.0f);

        //rgba

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void init(){
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        String title = "MyTitle";  // The title of the window, WARNING, if title is
        // null, the code will segfault at glfwCreateWindow()
        boolean resizable = true; // Whether or not the window is resizable

        int m_width = 1024; //width of the window
        int m_height = 768; //height of the window

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // Sets whether the window is resizable

        window = glfwCreateWindow(m_width, m_height, title, NULL, NULL); //Does the actual window creation
        if ( window == NULL ) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(window); //glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); //Will let lwjgl know we want to use this context as the
        //context to draw with
//GLContext.createFromCurrent(); // use this line instead with the 3.0.0a build
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwShowWindow(window);


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
    }


    public static void main(String[] args){
        GlfwTest g = new GlfwTest();
        g.run();
    }
}
