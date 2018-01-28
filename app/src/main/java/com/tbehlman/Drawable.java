package com.tbehlman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.support.annotation.CallSuper;

import static android.opengl.GLES20.*;

public abstract class Drawable {
    protected int programHandle;

    protected abstract int getVertexShader();

    protected abstract int getFragmentShader();

    @CallSuper
    public void onCreate() {
        final int vertexShaderHandle = compileShaderFromResource(GL_VERTEX_SHADER, getVertexShader());
        final int fragmentShaderHandle = compileShaderFromResource(GL_FRAGMENT_SHADER, getFragmentShader());

        programHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle);
    }

    @CallSuper
    public void onResize(int width, int height) {

    }

    @CallSuper
    public void onFrame() {
        glUseProgram(programHandle);
    }

    @CallSuper
    public void onDestroy() {

    }

    protected Buffer createBuffer(float[] floats) {
        return ByteBuffer.allocateDirect(floats.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(floats).position(0);
    }

    protected int getAttribute(String name) {
        return glGetAttribLocation(programHandle, name);
    }

    protected int getUniform(String name) {
        return glGetUniformLocation(programHandle, name);
    }

    private int compileShaderFromResource(final int shaderType, int resourceId) {
        InputStream inputStream = GLWallpaperService.getContext().getResources().openRawResource(resourceId);
        StringBuilder source = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = reader.readLine();
            while (line != null) {
                source.append(line);
                source.append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read shader resource: " + e.getMessage());
        }

        return compileShader(shaderType, source.toString());
    }

    /**
     * Helper function to compile a shader.
     *
     * @param shaderType The shader type.
     * @param shaderSource The shader source code.
     * @return An OpenGL handle to the shader.
     */
    private int compileShader(final int shaderType, final String shaderSource) {
        int shaderHandle = glCreateShader(shaderType);

        if (shaderHandle != 0) {
            // Pass in the shader source.
            glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            glGetShaderiv(shaderHandle, GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)  {
                String shaderInfoLog = glGetShaderInfoLog(shaderHandle);
                glDeleteShader(shaderHandle);
                throw new RuntimeException("Error compiling shader: " + shaderInfoLog);
            }
        }
        else {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    /**
     * Helper function to compile and link a program.
     *
     * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
     * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @return An OpenGL handle to the program.
     */
    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle) {
        int programHandle = glCreateProgram();

        if (programHandle != 0) {
            glAttachShader(programHandle, vertexShaderHandle);
            glAttachShader(programHandle, fragmentShaderHandle);

            // Link the two shaders together into a program.
            glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            glGetProgramiv(programHandle, GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)  {
                String programInfoLog = glGetProgramInfoLog(programHandle);
                glDeleteProgram(programHandle);
                throw new RuntimeException("Error compiling program: " + programInfoLog);
            }
        }
        else {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }
}
