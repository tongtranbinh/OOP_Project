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
    public float time;
    public Vector2 direction;
    public int owner;
    public int type;
    // ID = 0 1 2 3 4
    // wall = 0; player = 1;  enemy = 2; damagearea = 3; boss = 4;

    @Override
    public void reset() {
        direction =new Vector2 (0, -1);
        isAttack = false;
    }
}
