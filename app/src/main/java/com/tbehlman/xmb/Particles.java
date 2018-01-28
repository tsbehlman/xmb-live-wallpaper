package com.tbehlman.xmb;

import com.tbehlman.Drawable;
import com.tbehlman.Timer;
import com.tbehlman.VertexBufferObject;

import static android.opengl.GLES20.*;

public class Particles extends Drawable {
    protected static final int NUM_PARTICLES = 180;
    protected static final float PARTICLE_SIZE = 7.0f;

    protected final Settings settings;
    protected final Timer timer;

    protected VertexBufferObject seedBuffer;

    protected float ratio;
    protected float stepWidth;
    protected float stepHeight;

    public Particles(Settings settings, Timer timer) {
        super();
        this.settings = settings;
        this.timer = timer;
	}

	protected float[] getSeeds() {
        float[] seeds = new float[NUM_PARTICLES * 3];
        int numSeeds = 0;
        for( int i = 0; i < NUM_PARTICLES; i++ ) {
            seeds[ numSeeds++ ] = (float) Math.random();
            seeds[ numSeeds++ ] = (float) Math.random();
            seeds[ numSeeds++ ] = (float) ( Math.pow( Math.random(), 12.0 ) * PARTICLE_SIZE + 3.0 ) * 2.0f;
        }
        return seeds;
    }

	@Override
	protected int getVertexShader() {
		return R.raw.particle_vertex;
	}

	@Override
	protected int getFragmentShader() {
		return R.raw.particle_fragment;
	}

	@Override
    public void onCreate() {
        super.onCreate();
        seedBuffer = new VertexBufferObject(getSeeds());
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

        final int seedHandle = getAttribute("seed");
        glEnableVertexAttribArray(seedHandle);
        glBindBuffer(GL_ARRAY_BUFFER, seedBuffer.getIndex());
        glVertexAttribPointer(seedHandle, 3, GL_FLOAT, false, 0, 0);

        glUniform1f(getUniform("time"), timer.getTime());
        glUniform1f(getUniform("ratio"), ratio * 0.375f);

        glDrawArrays(GL_POINTS, 0, NUM_PARTICLES);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        seedBuffer.destroy();
    }
}
