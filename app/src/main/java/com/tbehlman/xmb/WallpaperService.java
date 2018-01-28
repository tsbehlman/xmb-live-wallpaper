package com.tbehlman.xmb;

import com.tbehlman.LifecycleRenderer;
import com.tbehlman.OpenGLES2WallpaperService;

public class WallpaperService extends OpenGLES2WallpaperService {
	@Override
	protected LifecycleRenderer getNewRenderer() {
		return new Renderer();
	}
}
