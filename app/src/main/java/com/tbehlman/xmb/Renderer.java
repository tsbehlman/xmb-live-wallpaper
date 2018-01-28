package com.tbehlman.xmb;

import com.tbehlman.Drawable;
import com.tbehlman.GLWallpaperService;
import com.tbehlman.LifecycleRenderer;
import com.tbehlman.Timer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class Renderer implements LifecycleRenderer {
    protected final List<Drawable> drawables;
    protected final Settings settings;
    protected final Timer timer;

    public Renderer() {
        settings = new Settings(GLWallpaperService.getContext());
        timer = new Timer(0.25);

        drawables = new ArrayList<>();
        drawables.add(new Background(settings));
        drawables.add(new Flow(settings, timer));
        drawables.add(new Particles(settings, timer));
    }

	@Override
	public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glDepthFunc(GL_LESS);
        glDepthRangef(0.0f, 1.0f);
        glDisable(GL_DITHER);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        settings.onCreate();
        for (Drawable drawable : drawables) {
            drawable.onCreate();
        }
	}

	@Override
	public void onSurfaceChanged(GL10 gl10, int width, int height) {
		glViewport(0, 0, width, height);


        for (Drawable drawable : drawables) {
            drawable.onResize(width, height);
        }

        if (timer.getTime() > 25000.0f) {
            timer.reset();
        }
	}

	@Override
	public void onDrawFrame(GL10 gl10) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (Drawable drawable : drawables) {
            drawable.onFrame();
        }

        glFlush();
	}

    public void onDestroy() {
        settings.onDestroy();

        for (Drawable drawable : drawables) {
            drawable.onDestroy();
        }
    }
}
