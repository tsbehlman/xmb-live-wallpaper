package com.tbehlman.xmb;

import com.tbehlman.Drawable;
import com.tbehlman.Timer;
import com.tbehlman.VertexBufferObject;

import static android.opengl.GLES20.*;

public class Flow extends Drawable {
    protected static final int RESOLUTION = 50;

    protected static final int NUM_VERTICES = RESOLUTION * RESOLUTION + ( RESOLUTION - 2 ) * ( RESOLUTION + 2 ) + 2;
    protected static final float STEP = 2.0f / RESOLUTION;

    protected final Settings settings;
    protected final Timer timer;

    protected VertexBufferObject vertexBuffer;

    protected float ratio;
    protected float stepWidth;
    protected float stepHeight;

    public Flow(Settings settings, Timer timer) {
        super();
        this.settings = settings;
        this.timer = timer;
	}

	protected float[] getVertices() {
        float[] vertices = new float[NUM_VERTICES * 2];
        int numVertices = 0;
        for( int x = 1; x < RESOLUTION; x++ ) {
            final float xPos1 = (float) ( x - 1 ) / ( RESOLUTION - 1 ) * 2 - 1;
            final float xPos2 = (float) x / ( RESOLUTION - 1 ) * 2 - 1;
            vertices[ numVertices++ ] = xPos2;
            vertices[ numVertices++ ] = -1;
            for( int y = 0; y < RESOLUTION; y++ ) {
                float yPos = (float) y / ( RESOLUTION - 1 ) * 2 - 1;
                vertices[ numVertices++ ] = xPos2;
                vertices[ numVertices++ ] = yPos;

                vertices[ numVertices++ ] = xPos1;
                vertices[ numVertices++ ] = yPos;
            }
            vertices[ numVertices++ ] = xPos1;
            vertices[ numVertices++ ] = 1;
        }

        return vertices;
    }

	@Override
	protected int getVertexShader() {
		return R.raw.flow_vertex;
	}

	@Override
	protected int getFragmentShader() {
		return R.raw.flow_fragment;
	}

	@Override
    public void onCreate() {
        super.onCreate();
        vertexBuffer = new VertexBufferObject(getVertices());
    }

    @Override
    public void onResize(int width, int height) {
        super.onResize(width, height);

        ratio = (float) Math.max( 1, Math.min( width / height, 2 ) );
        stepWidth = 1.0f / width;
        stepHeight = 1.0f / height;
    }

    @Override
    public void onFrame() {
        super.onFrame();

        final int positionHandle = getAttribute("position");
        glEnableVertexAttribArray(positionHandle);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer.getIndex());
        glVertexAttribPointer(positionHandle, 2, GL_FLOAT, false, 0, 0);

        glUniform1f(getUniform("time"), timer.getTime());
        glUniform1f(getUniform("opacity"), settings.getOpacity());
        glUniform1f(getUniform("ratio"), ratio * 0.375f);
        glUniform1f(getUniform("step"), STEP);

        glEnable(GL_DEPTH_TEST);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, NUM_VERTICES);
        glDisable(GL_DEPTH_TEST);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vertexBuffer.destroy();
    }
}
