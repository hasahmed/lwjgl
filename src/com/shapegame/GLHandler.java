package com.shapegame;

/*
 * Project : shapegame
 * Package : com.shapegame
 * Creator : Hasan Yusuf Ahmed
 * Date    : 3/12/18
 */
import com.shapegame.shapes.Shape;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

class GLHandler {
    private int progHandle;
    private int squareVertexBuffer;
    private int trinagleVertexBuffer;
    private ArrayList<Shape> shapeList;
    private float[] squareVerts;
    private GLUtil glUtilHandle;
    GLHandler(GLUtil glUtilInstance){
        this.glUtilHandle = glUtilInstance;
        shapeList = new ArrayList<>(1000); //big initial capacity so no need to resize for a long time

        initilizeVerts(); //delete later
        //call first
        GL.createCapabilities();
        glClearColor(0f, 1f, 1f, 0.5f);

        // enable alpha
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        //THE VAO: Vertex Array Object
        // VAO: Stores all the state needed to supply vertex data to the GPU
        int vertextArrayID = glGenVertexArrays(); // creation of VAO
        glBindVertexArray(vertextArrayID);

        int triangleVertexArrayID = glGenVertexArrays(); //VAO for triangle
        trinagleVertexBuffer = glGenBuffers();


        //loading square into buffer
        squareVertexBuffer = glGenBuffers(); //generate buffers to hold the square

        //<<<<<<<<<<<<SHADERS>>>>>>>>>>>>>>
        String[] shaderBufferArr = glUtilHandle.readinShaders("test.frag", "test.vert"); //read shaders into string array
        int vertShaderID = glCreateShader(GL_VERTEX_SHADER);
        int fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShaderID, shaderBufferArr[0]);
        glShaderSource(vertShaderID, shaderBufferArr[1]);

        glCompileShader(fragShaderID);
        glCompileShader(vertShaderID);


        progHandle = glCreateProgram();
        glAttachShader(progHandle, fragShaderID);
        glAttachShader(progHandle, vertShaderID);
        glLinkProgram(progHandle);

        glDetachShader(progHandle, vertShaderID);
        glDetachShader(progHandle, fragShaderID);


        glDeleteShader(vertShaderID);
        glDeleteShader(fragShaderID);

        glUseProgram(progHandle);
        //END <<SHADERS>>

    }

    void initilizeVerts(){
        squareVerts = glUtilHandle.makeSquare(40, 40, 50);
    }


    //void drawShape(DrawAbleShape )

    void drawTriangles(float r, float g, float b, float a) {
        glUniform4fv(glGetUniformLocation(progHandle, "incolor"), new float[]{r, g, b, a});
        glBindBuffer(GL_ARRAY_BUFFER, trinagleVertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, glUtilHandle.triangleVerts, GL_STATIC_DRAW);
        glUtilHandle.getRuntimeShaderString("taco");

        //square attribute array creation
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glDrawArrays(GL_TRIANGLES, 0, glUtilHandle.triangleVerts.length / 3);

        glDisableVertexAttribArray(0);
        glUtilHandle.translate(glUtilHandle.triangleVerts, -1, -1);
    }

    void drawSquares(float r, float g, float b, float a){
        glUniform4fv(glGetUniformLocation(progHandle, "incolor"), new float[]{r, g, b, a});
        glBindBuffer(GL_ARRAY_BUFFER, squareVertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, squareVerts, GL_STATIC_DRAW);


        //square attribute array creation
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glDrawArrays(GL_TRIANGLES, 0, squareVerts.length / 3);

        glDisableVertexAttribArray(0);
        glUtilHandle.translate(squareVerts, 1, -1);
    }
}
