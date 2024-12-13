package com.Code.Entity.System;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.Code.Main.MAX_STEP_TIME;
import static com.Code.Main.bodyDef;

public class PhysicDebugSystem extends IteratingSystem {
    Main game;


    public PhysicDebugSystem(Main game) {
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
    }


    @Override
    public void update(float deltaTime) {

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(entity);
        System.out.println(playerComponent.life);
        if(playerComponent.life <= 0) {
            System.out.println(" ban da bi dam chet");
        }
    }
}
