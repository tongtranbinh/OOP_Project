package com.Code.Entity.Component;

import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class EntityComponent implements Component, Pool.Poolable {
    public int life;
    public int maxLife;
    public float speed;
    public DirectionType direction;
    public Vector2 startPosition;

    @Override
    public void reset() {
        this.direction = DirectionType.DOWN;
        this.life = 0;
        this.maxLife = 0;
        this.speed = 0.0F;
    }
}
