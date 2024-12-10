package com.Code.Entity.Component;

import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent extends EntityComponent  {
    public int count = 0;
    public boolean focus ;
    public boolean stop ;
    public boolean isAttack;
    public boolean isAttacking;
    public float time;
    public float timeSinceLastShot;
    @Override
    public void reset() {
        super.reset();
        focus = false;
    }
}
