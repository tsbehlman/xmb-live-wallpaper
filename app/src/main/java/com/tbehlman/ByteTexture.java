package com.tbehlman;

/**
 * Created by Trev on 2/28/18.
 */

import java.nio.ByteBuffer;

import static android.opengl.GLES20.*;

public class ByteTexture {
    protected final int[] textureHandles = new int[1];

    public ByteTexture(int width, int height, char[] data) {
        glGenTextures(1, textureHandles, 0);
        glBindTexture(GL_TEXTURE_2D, textureHandles[0]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        for( int i = 0; i < data.length; i++ ) {
            buffer.put( i, (byte) data[ i ] );
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, width, height, 0, GL_ALPHA, GL_UNSIGNED_BYTE, buffer);
    }

    public void bind(int textureUnit, int textureIndex, int uniformHandle) {
        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_2D, textureHandles[0]);
        glUniform1i(uniformHandle, textureIndex);
    }

    public void destroy() {
        glDeleteTextures(1, textureHandles, 0);
        textureHandles[0] = 0;
    }
}
