package com.Code.Entity.System;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.DamageAreaComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import static com.Code.Entity.ECSEngine.box2DComponentMapper;
import static java.lang.Math.abs;

public class DamageAreaSystem extends IteratingSystem {

    Main game;

    public DamageAreaSystem(Main game) {
        super(Family.all(Box2DComponent.class, DamageAreaComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final Box2DComponent box2DComponent = box2DComponentMapper.get(entity);
        final DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(entity);
        Vector2 damageAreaPosition = box2DComponent.body.getPosition();

        if (damageAreaComponent.isAttack) {
            Vector2 velocity = damageAreaComponent.direction;
            Vector2 force = Vector2.Zero;
            force.x = velocity.x * damageAreaComponent.speed;
            force.y = velocity.y * damageAreaComponent.speed;
            box2DComponent.body.setLinearVelocity(force);
        }


        if(damageAreaComponent.time <= 0) {
            box2DComponent.body.setLinearVelocity(Vector2.Zero);
            game.ecsEngine.EntityQueue.add(entity);
        } else damageAreaComponent.time -= deltaTime;


    }
}
