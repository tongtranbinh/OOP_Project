package com.Code.Effect;

import com.Code.Others.DirectionType;
import com.badlogic.gdx.math.Vector2;

public class DamageArea {
    public boolean isAttack;
    public Vector2 position;
    public Vector2 direction;
    public int damage;
    public int owner;
    public float speed;
    public int type;
    public float time;
    public float width;
    public float height;
    public DamageArea(Vector2 position, Vector2 direction, float width, float height, int damage, float speed, float time, int owner,int type, boolean isAttack) {
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.damage = damage;
        this.speed = speed;
        this.time = time;
        this.isAttack = isAttack;
        this.owner = owner;
        this.type = type;
    }
}
