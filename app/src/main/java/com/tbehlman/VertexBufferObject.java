package com.tbehlman;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.opengl.GLES20.*;

public class VertexBufferObject {
    protected final int[] bufferIndices = new int[1];

    public VertexBufferObject(float[] vertices) {
        glGenBuffers(1, bufferIndices, 0);
        glBindBuffer(GL_ARRAY_BUFFER, bufferIndices[0]);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * 4, createBuffer(vertices), GL_STATIC_DRAW);
    }

    protected Buffer createBuffer(float[] floats) {
        return ByteBuffer.allocateDirect(floats.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(floats).position(0);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, bufferIndices[0]);
    }

    public void destroy() {
        glDeleteBuffers(1, bufferIndices, 0);
        bufferIndices[0] = 0;
    }
}
