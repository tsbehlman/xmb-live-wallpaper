package com.tbehlman;

import android.opengl.GLSurfaceView;

public interface LifecycleRenderer extends GLSurfaceView.Renderer {
    void onDestroy();
}
