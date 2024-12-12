package com.Code.Entity.System;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.EnemyComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.Code.Others.SteerableAgent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class EnemyMovementSystem extends IteratingSystem {

    Main game;

    public float Duration = 0;
    public float limitDuration = 5f;
    private SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());



    public EnemyMovementSystem(Main game) {
        super(Family.all(EnemyComponent.class, Box2DComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        final EnemyComponent enemyComponent = ECSEngine.enemyComponentMapper.get(entity);
        final Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
        final PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(getPlayerEntity());
        final Box2DComponent playerBox2DComponent = ECSEngine.box2DComponentMapper.get(getPlayerEntity());

        Vector2 playerPos = playerBox2DComponent.body.getPosition();
        Vector2 enemyPos = box2DComponent.body.getPosition();

        float distance = playerPos.dst(enemyPos);

        final Vector2 speed = new Vector2(enemyComponent.speed * 0.3f, enemyComponent.speed * 0.3f);
        if (!enemyComponent.stop) {

            if ( distance < 100 * Main.PPM ) {
                enemyComponent.focus = true;
                // Đuổi theo Player
                Box2DComponent b2dPlayer = ECSEngine.box2DComponentMapper.get(getPlayerEntity());
                SteerableAgent enemySteerable = new SteerableAgent(box2DComponent.body, 1.5f);
                SteerableAgent playerSteerable = new SteerableAgent(b2dPlayer.body, 1.5f);

                Arrive<Vector2> arriveBehavior = new Arrive<>(enemySteerable, playerSteerable)
                    .setArrivalTolerance(0.1f)
                    .setDecelerationRadius(3)
                    .setTimeToTarget(0.1f);
                arriveBehavior.calculateSteering(steeringOutput);
                Vector2 force = steeringOutput.linear.scl(deltaTime);
                box2DComponent.body.applyForceToCenter(force, true);

                // Limit the velocity to prevent the entity from moving too fast
                Vector2 velocity = box2DComponent.body.getLinearVelocity();
                float maxSpeed = enemyComponent.timeSinceLastShot >=1f ? enemyComponent.speed : 0.008f;
                if (velocity.len() > maxSpeed) {
                    velocity = velocity.nor().scl(maxSpeed);
                    box2DComponent.body.setLinearVelocity(velocity);
                }
            } else {
                enemyComponent.focus = false;
                // Quay về vị trí ban đầu nếu không đúng tại startPosition
                if (!enemyPos.epsilonEquals(enemyComponent.startPosition, 0.1f)) {
                    Vector2 dir = new Vector2(enemyComponent.startPosition.x - enemyPos.x, enemyComponent.startPosition.y - enemyPos.y);
                    dir.nor();
                    dir.set(dir.x * enemyComponent.speed *0.5f * box2DComponent.body.getMass(), dir.y * enemyComponent.speed *0.5f * box2DComponent.body.getMass());
//                    box2DComponent.body.setTransform(box2DComponent.body.getPosition().x + dir.x * deltaTime*0.5f ,
//                            box2DComponent.body.getPosition().y + dir.y * deltaTime*0.5f ,
//                            box2DComponent.body.getAngle());
                    box2DComponent.body.applyForceToCenter(dir, true);
                } else {
                    // Di chuyển ngẫu nhiên xung quanh startPosition
                    SteerableAgent enemySteerable = new SteerableAgent(box2DComponent.body, 1.5f);
                    enemySteerable.setMaxLinearSpeed(4000); // Tăng tốc độ tối đa
                    enemySteerable.setMaxLinearAcceleration(2000); // Tăng gia tốc tối đa

                    Wander<Vector2> wanderSB = new Wander<>(enemySteerable) //
                        .setFaceEnabled(false) // We want to use Face internally (independent facing is on)
                        .setAlignTolerance(0.01f) // Used by Face
                        .setDecelerationRadius(5) // Used by Face
                        .setTimeToTarget(0.1f) // Used by Face and Arrive
                        .setWanderOffset(60) //
                        .setWanderOrientation(random.nextFloat() * 360) //
                        .setWanderRadius(40) //
                        .setWanderRate(MathUtils.PI2 * 4);

                    wanderSB.calculateSteering(steeringOutput);
                    Vector2 force = steeringOutput.linear.scl(deltaTime);
                    box2DComponent.body.applyForceToCenter(force.scl(2), true); // Tăng lực được áp dụng

                    // Limit the velocity to prevent the entity from moving too fast
                    Vector2 velocity = box2DComponent.body.getLinearVelocity();// Tăng tốc độ tối đa
                    if (velocity.len() > enemyComponent.speed) {
                        velocity = velocity.nor().scl(enemyComponent.speed);
                        box2DComponent.body.setLinearVelocity(velocity);
                    }
                }
            }
            enemyComponent.timeSinceLastShot += deltaTime;

        }
        enemyComponent.time += deltaTime;
//        if(distance < 1.25f) {
//            if(!enemyComponent.isAttack && enemyComponent.time >= enemyComponent.reload) {
//                enemyComponent.isAttack = true;
//                enemyComponent.isAttacking = false;
//                enemyComponent.time = 0;
//            }
//        }
    }

    public Entity getPlayerEntity() {
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        if (entities.size() > 0) {
            return entities.first();
        }
        return null;
    }
}
