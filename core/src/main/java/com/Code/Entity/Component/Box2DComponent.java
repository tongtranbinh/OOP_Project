package com.Code.Entity.Component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class Box2DComponent implements Component , Pool.Poolable{
    public Body body;
    public boolean isDead;
    public int ID;
    // ID = 0 1 2 3 4 5
    // wall player enemy damagearea

    public Box2DComponent(){
        ID = 0;
    }
    @Override
    public void reset() {
        Vector2 dir = new Vector2(1,1);
        dir.nor();
        ID = 0;
        isDead = false;
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }
}
