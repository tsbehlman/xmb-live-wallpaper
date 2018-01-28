package com.tbehlman;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public abstract class GLWallpaperService extends WallpaperService {
	private static Context context;

	@Override
	public void onCreate() {
		context = this.getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}

	public class GLEngine extends Engine {
		class WallpaperGLSurfaceView extends GLSurfaceView {

			WallpaperGLSurfaceView(Context context) {
				super(context);
			}

			@Override
			public SurfaceHolder getHolder() {
				return getSurfaceHolder();
			}

			public void onDestroy() {
				super.onDetachedFromWindow();
			}
		}

		private WallpaperGLSurfaceView glSurfaceView;
		private LifecycleRenderer renderer;

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			glSurfaceView = new WallpaperGLSurfaceView(GLWallpaperService.this);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			super.onVisibilityChanged(visible);

			if (renderer != null) {
				if (visible) {
					glSurfaceView.onResume();
				} else {					
					glSurfaceView.onPause();														
				}
			}
		}		

		@Override
		public void onDestroy() {
			super.onDestroy();
			glSurfaceView.onDestroy();
            this.renderer.onDestroy();
		}
		
		protected void setRenderer(LifecycleRenderer renderer) {
			glSurfaceView.setRenderer(renderer);
			this.renderer = renderer;
		}
		
		protected void setPreserveEGLContextOnPause(boolean preserve) {
			glSurfaceView.setPreserveEGLContextOnPause(preserve);
		}		

		protected void setEGLContextClientVersion(int version) {
			glSurfaceView.setEGLContextClientVersion(version);
		}
	}
}
