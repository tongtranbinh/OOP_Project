package com.Code.Map;

import com.Code.Main;

public class CollisionArea {
    private final float x;
    private final float y;

    private float[] vertices;

    public CollisionArea (final float x, final float y, float[] vertices) {
        this.x = x * Main.PPM;
        this.y = y * Main.PPM;
        this.vertices = vertices;
        for (int i=0; i<vertices.length; i += 2) {
            vertices[i] = vertices[i] * Main.PPM;
            vertices[i+1] = vertices[i+1] * Main.PPM;
        }

    }


    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }


    public float[] getVertices() {
        return vertices;
    }
}
