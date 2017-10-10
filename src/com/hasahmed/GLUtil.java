package com.hasahmed;

/**
 * Created by Hasan Y Ahmed on 10/9/17.
 */

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.lwjgl.opengl.*;

public class GLUtil {

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
    static String[] readinShaders(String fragShader, String vertShader) {
        System.out.println("Reading in Frag Shader...");
        String fragmentShaderBuffer = readFile(fragShader, Charset.defaultCharset());
        System.out.println("Reading in Vert Shader...");
        String vertexShaderBuffer = readFile(vertShader, Charset.defaultCharset());

        if (fragmentShaderBuffer != null && vertexShaderBuffer != null) {
            String[] shaderArr = {fragmentShaderBuffer, vertexShaderBuffer};
            return shaderArr;
        }
        throw new UnsupportedOperationException("There was a problem reading in the shaders");
    }
}
