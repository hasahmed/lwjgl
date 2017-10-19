package com.shapegame;

/**
 * Created by Hasan Y Ahmed on 10/9/17.
 */

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class GLUtil {
    static float horPixelStep = 2f / 800f;
    static float vertPixelStep = 2f / 600f;

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


    static String getRuntimeShaderString(String shaderFileName){
        InputStream fragStream = ClassLoader.getSystemClassLoader().getResourceAsStream("shaders/" + shaderFileName);
        if (fragStream == null) //the reading in of the shader has gone wrong. Will default to default shaders
            return null;


        byte[] frShadeByteBuffer;
        try {
            frShadeByteBuffer = new byte[fragStream.available()];
            fragStream.read(frShadeByteBuffer);
        } catch(IOException e){
            return null;
        }
        return new String(frShadeByteBuffer, Charset.defaultCharset());
    }

    static String[] readinShaders(String fragShader, String vertShader) {
        String fragmentShaderBuffer = getRuntimeShaderString(fragShader);
        String vertexShaderBuffer = getRuntimeShaderString(vertShader);
        if (fragmentShaderBuffer == null || vertexShaderBuffer == null){
            System.out.println("Error in reading in of shaders. CHECK SPELLING. Using default shaders");
            fragmentShaderBuffer =
                    "#version 330 core\n" +
                            "out vec3 color;\n" +
                            "uniform vec3 incolor;\n" +
                            "void main(){\n" +
                            "    color = incolor;\n" +
                            "}";
            vertexShaderBuffer =
                    "#version 330 core\n" +
                            "layout(location=0) in vec3 vert;\n" +
                            "void main(){\n" +
                            "    gl_Position.xyz = vert;\n" +
                            "    gl_Position.w = 1.0;\n" +
                            "}";
        }
        return new String[]{fragmentShaderBuffer, vertexShaderBuffer};
    }

    static float[] makeCircle(float cx, float cy, float r, int num_segments){
        float[] f = new float[num_segments * 3];
        for(int ii = 0; ii < num_segments * 3; ii += 3) {
            float theta = 2.0f * 3.1415926f * (float)ii / (float)num_segments;//get the current angle

            float x = r * (float)Math.cos(theta);//calculate the x component
            float y = r * (float)Math.sin(theta);//calculate the y component

            f[ii] = x + cx;
            f[ii + 1] = y + cy;
            f[ii + 2] = 0f;
            System.out.printf("%ff, %ff, 0f,\n", x + cx, y + cy);
        }
        return f;
    }


    static float[] arrayAppend(float[] arr1, float[] arr2){
        float[] ret = new float[arr1.length + arr2.length];
        int i;
        for(i = 0; i < arr1.length; i++)
            ret[i] = arr1[i];

        for(int j = 0; j < arr2.length; j++) {
            ret[i] = arr2[j];
            i++;
        }

        return ret;
    }


    static float[] makeSquare(int screenx, int screeny, int size) {
        float x = -1f + ((float)screenx * horPixelStep);
        float y = 1f - ((float)screeny * vertPixelStep);
        float xsize = (float)size * horPixelStep;
        float ysize = (float)size * vertPixelStep;
        float squareVerts[] = {
                // triangle 1
                x,              y - ysize,   0f, //lower left,
                x + xsize,      y - ysize,   0f, //lower right
                x,              y,           0f, // top left


                // triangle 2
                x,              y,           0f, // top left
                x + xsize,      y - ysize,   0f, //lower right
                x + xsize,      y,           0f //lower right
        };
        return squareVerts;
    }

    static void translate(float[] square, int x, int y){
        float realx = x * horPixelStep;
        float realy = y * vertPixelStep;
        for(int i = 0; i < square.length; i += 3){
            square[i] += realx;
            square[i + 1] += realy;
        }

    }


}
