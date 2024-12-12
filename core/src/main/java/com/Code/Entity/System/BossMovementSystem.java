package com.Code.Entity.System;

import com.Code.Entity.Component.AnimationComponent;
import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.SteerableAgent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;

public class BossMovementSystem extends IteratingSystem {

    Main game;
    private static SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());

    public BossMovementSystem(Main game) {
        super(Family.all(BossComponent.class, Box2DComponent.class).get());
        this.game = game;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BossComponent bossCmp = ECSEngine.bossComponentMapper.get(entity);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
        // Đuổi theo Player

        if (getPlayerEntity() == null) return;
        Box2DComponent b2dPlayer = ECSEngine.box2DComponentMapper.get(getPlayerEntity());

        Vector2 playerPos = b2dPlayer.body.getPosition();
        Vector2 bossPos = box2DComponent.body.getPosition();
        float distance = bossPos.dst(playerPos);
        SteerableAgent enemySteerable = new SteerableAgent(box2DComponent.body, 1.5f);
        SteerableAgent playerSteerable = new SteerableAgent(b2dPlayer.body, 1.5f);

        if (distance < 200 * Main.PPM) bossCmp.readytoAttack = true;
        if (bossCmp.readytoAttack && !bossCmp.stop) {
            Arrive<Vector2> arriveBehavior = new Arrive<>(enemySteerable, playerSteerable)
                .setArrivalTolerance(0.1f)
                .setDecelerationRadius(3)
                .setTimeToTarget(0.1f);
            arriveBehavior.calculateSteering(steeringOutput);
            Vector2 force = steeringOutput.linear.scl(deltaTime);
            force.set(force.x , force.y );
            box2DComponent.body.applyForceToCenter(force, true);

            // Limit the velocity to prevent the entity from moving too fast
            Vector2 velocity = box2DComponent.body.getLinearVelocity();
            float maxSpeed = 7f;
            if (velocity.len() > maxSpeed) {
                velocity = velocity.nor().scl(maxSpeed);
                box2DComponent.body.setLinearVelocity(velocity);
            }
        }
        if(bossCmp.stop){
            box2DComponent.body.setLinearVelocity(0,0);
        }
    }

    public Entity getPlayerEntity () {
        ImmutableArray<Entity> entities = game.getEcsEngine().getEntitiesFor(Family.all(PlayerComponent.class).get());
        if (entities.size() > 0) {
            return entities.first();
        }
        return null;
    }
}

