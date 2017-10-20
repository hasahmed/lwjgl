package com.shapegame;

/**
 * Created by Hasan Y Ahmed on 10/20/17.
 */
import org.lwjgl.opengl.*;

class GLHandler {
    private int progHandle;
    private int squareVertexBuffer;
    private int trinagleVertexBuffer;
    private float[] squareVerts;
    GLHandler(){
        initilizeVerts(); //delete later
        //call first
        GL.createCapabilities();
        GL11.glClearColor(0f, 1f, 1f, 1f);


        //THE VAO: Vertex Array Object
        // VAO: Stores all the state needed to supply vertex data to the GPU
        int vertextArrayID = GL30.glGenVertexArrays(); // creation of VAO
        GL30.glBindVertexArray(vertextArrayID);

        int triangleVertexArrayID = GL30.glGenVertexArrays(); //VAO for triangle
        trinagleVertexBuffer = GL15.glGenBuffers();


        //loading square into buffer
        squareVertexBuffer = GL15.glGenBuffers(); //generate buffers to hold the square

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

        GL20.glUseProgram(progHandle);
        //END <<SHADERS>>

    }

    void initilizeVerts(){
        squareVerts = GLUtil.makeSquare(40, 40, 50);
    }

    void drawTriangles(float r, float g, float b) {
        GL20.glUniform3fv(GL20.glGetUniformLocation(progHandle, "incolor"), new float[]{r, g, b});
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, trinagleVertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, GLUtil.Constants.triangleVerts, GL15.GL_STATIC_DRAW);

        //square attribute array creation
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, GLUtil.Constants.triangleVerts.length / 3);

        GL20.glDisableVertexAttribArray(0);
        GLUtil.translate(GLUtil.Constants.triangleVerts, -1, -1);
    }

    void drawSquares(float r, float g, float b){
        GL20.glUniform3fv(GL20.glGetUniformLocation(progHandle, "incolor"), new float[]{r, g, b});
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, squareVertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, squareVerts, GL15.GL_STATIC_DRAW);


        //square attribute array creation
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, squareVerts.length / 3);

        GL20.glDisableVertexAttribArray(0);
        GLUtil.translate(squareVerts, 1, -1);


    }
}
