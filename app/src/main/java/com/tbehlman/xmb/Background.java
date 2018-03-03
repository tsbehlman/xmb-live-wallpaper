package com.tbehlman.xmb;

import com.tbehlman.ByteTexture;
import com.tbehlman.Drawable;
import com.tbehlman.VertexBufferObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.opengl.GLES20.*;

public class Background extends Drawable {
    protected final Settings settings;

    protected VertexBufferObject vertexBuffer;
    protected ByteTexture bayerTexture;

    protected final float[] backgroundColor = new float[3];
    protected final float[] resolution = new float[2];

    public Background(Settings settings) {
        super();
        this.settings = settings;
    }

    @Override
    protected int getVertexShader() {
        return R.raw.background_vertex;
    }

    @Override
    protected int getFragmentShader() {
        return R.raw.background_fragment;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vertexBuffer = new VertexBufferObject(new float[] {
                +1.0f, -1.0f,
                -1.0f, -1.0f,
                +1.0f, +1.0f,
                -1.0f, +1.0f
        });
        bayerTexture = new ByteTexture(8, 8, new char[]{
                  0, 128,  32, 160,   8, 136,  40, 168,
                192,  64, 224,  96, 200,  72, 232, 104,
                 48, 176,  16, 144 , 56, 184,  24, 152,
                240, 112, 208,  80, 248, 120, 216,  88,
                 12, 140,  44, 172,   4, 132,  36, 164,
                204,  76, 236, 108, 196,  68, 228, 100,
                 60, 188,  28, 156,  52, 180,  20, 148,
                252, 124, 220,  92, 244, 116, 212,  84
        });
    }

    @Override
    public void onResize(int width, int height) {
        super.onResize(width, height);
        resolution[0] = width;
        resolution[1] = height;
    }

    @Override
    public void onFrame() {
        super.onFrame();

        final int positionHandle = getAttribute("position");
        glEnableVertexAttribArray(positionHandle);
        vertexBuffer.bind();
        glVertexAttribPointer(positionHandle, 2, GL_FLOAT, false, 0, 0);

        float[] color = settings.getColor();
        Calendar calendar = GregorianCalendar.getInstance();
        double day = ( calendar.get( Calendar.HOUR_OF_DAY ) + calendar.get( Calendar.MINUTE ) / 60.0 ) / 24.0;
        double brightness = ( 1.0 - Math.cos( day * 2.0 * Math.PI ) ) / 1.75;
        backgroundColor[0] = color[0] * (float) brightness;
        backgroundColor[1] = color[1] * (float) brightness;
        backgroundColor[2] = color[2] * (float) brightness;
        glUniform3fv(getUniform("color"), 1, backgroundColor, 0);
        glUniform2fv(getUniform("resolution"), 0, resolution, 0);
        bayerTexture.bind(GL_TEXTURE0, 0, getUniform("bayerTexture"));

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vertexBuffer.destroy();
        bayerTexture.destroy();
    }
}
