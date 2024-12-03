package com.Code.Entity.System;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.EnemyComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class EnemyMovementSystem extends IteratingSystem {

    Main game;
    public EnemyMovementSystem(Main game) {
        super(Family.all(EnemyComponent.class, Box2DComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        final EnemyComponent enemyComponent = ECSEngine.enemyComponentMapper.get(entity);
        final Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);

        box2DComponent.body.setLinearVelocity(Vector2.Zero);
    }
}
