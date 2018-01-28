package com.tbehlman.xmb;

public class Color {
    public static float[] fromHex(String hexCode) {
        int rgb = Integer.parseInt(hexCode, 16);
        int r = ( rgb >> 16 ) & 0xff;
        int g = ( rgb >> 8  ) & 0xff;
        int b =   rgb         & 0xff;
        return new float[] { r / 255.0f, g / 255.0f, b / 255.0f };
    }
}
