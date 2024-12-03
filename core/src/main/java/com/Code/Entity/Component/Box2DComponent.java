package com.Code.Entity.Component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class Box2DComponent implements Component , Pool.Poolable{
    public Body body;

    @Override
    public void reset() {

        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }
}
