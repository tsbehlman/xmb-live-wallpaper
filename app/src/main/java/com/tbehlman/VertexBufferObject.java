package com.tbehlman;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class VertexBufferObject {
    protected final int[] bufferIndices = new int[1];

    public VertexBufferObject(float[] vertices) {
        glGenBuffers(1, bufferIndices, 0);
        glBindBuffer(GL_ARRAY_BUFFER, bufferIndices[0]);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * 4, FloatBuffer.wrap(vertices), GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, bufferIndices[0]);
    }

    public void destroy() {
        glDeleteBuffers(1, bufferIndices, 0);
        bufferIndices[0] = 0;
    }
}
