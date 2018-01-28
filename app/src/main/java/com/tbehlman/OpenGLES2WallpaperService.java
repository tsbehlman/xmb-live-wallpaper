package com.tbehlman;

import android.view.SurfaceHolder;

public abstract class OpenGLES2WallpaperService extends GLWallpaperService {
	@Override
	public Engine onCreateEngine() {
		return new OpenGLES2Engine();
	}
	
	class OpenGLES2Engine extends GLWallpaperService.GLEngine {

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// Request an OpenGL ES 2.0 compatible context.
			setEGLContextClientVersion(2);

			// On Honeycomb+ devices, this improves the performance when
			// leaving and resuming the live wallpaper.
			setPreserveEGLContextOnPause(true);

			// Set the renderer to our user-defined renderer.
			setRenderer(getNewRenderer());
		}
	}	
	
	protected abstract LifecycleRenderer getNewRenderer();
}
