package com.Code.Entity.Component;

import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class DamageAreaComponent implements Component, Pool.Poolable {

    public boolean isAttack;
    public int damage;
    public float speed;
    public Vector2 position;
    public float range;
    public float time;
    public Vector2 direction;

    @Override
    public void reset() {
        direction =new Vector2 (0, -1);
        isAttack = false;
    }
}
