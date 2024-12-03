package com.Code.Effect;

import com.Code.Others.DirectionType;
import com.badlogic.gdx.math.Vector2;

public class DamageArea {
    public boolean isAttack;
    public Vector2 position;
    public DirectionType direction;
    public int damage;
    public float speed;
    public float range;
    public float width;
    public float height;
    public DamageArea(Vector2 position, DirectionType direction, float width, float height, int damage, float speed, float range, boolean isAttack) {
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.isAttack = isAttack;
    }
}
