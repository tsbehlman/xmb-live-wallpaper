package com.tbehlman.xmb;

import com.tbehlman.Drawable;
import com.tbehlman.VertexBufferObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.opengl.GLES20.*;

public class Background extends Drawable {
    protected final Settings settings;

    protected VertexBufferObject vertexBuffer;

    protected final float[] backgroundColor = new float[3];

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
    }

    @Override
    public void onFrame() {
        super.onFrame();

        final int positionHandle = getAttribute("position");
        glEnableVertexAttribArray(positionHandle);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer.getIndex());
        glVertexAttribPointer(positionHandle, 2, GL_FLOAT, false, 0, 0);

        float[] color = settings.getColor();
        Calendar calendar = GregorianCalendar.getInstance();
        double day = ( calendar.get( Calendar.HOUR_OF_DAY ) + calendar.get( Calendar.MINUTE ) / 60.0 ) / 24.0;
        double brightness = ( 1.0 - Math.cos( day * 2.0 * Math.PI ) ) / 1.75;
        backgroundColor[0] = color[0] * (float) brightness;
        backgroundColor[1] = color[1] * (float) brightness;
        backgroundColor[2] = color[2] * (float) brightness;
        glUniform3fv(getUniform("color"), 1, backgroundColor, 0);

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vertexBuffer.destroy();
        bayerTexture.destroy();
    }
}
