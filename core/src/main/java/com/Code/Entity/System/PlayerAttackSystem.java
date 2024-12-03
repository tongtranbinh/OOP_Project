package com.Code.Entity.System;

import com.Code.Effect.DamageArea;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

public class PlayerAttackSystem extends IteratingSystem {

    Main game;
    boolean isAttack1 = false;
    boolean isAttack2 = false;
    boolean readyAttack1 = true;
    boolean stop = false;
    float speed;
    float range;
    int damage;
    float time;
    float timeReload = 0.5f;


    public PlayerAttackSystem(Main game) {
        super(Family.all(PlayerComponent.class, Box2DComponent.class).get());
        this.game = game;
        time = 0;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(entity);
        final Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);

        Input();

        if (!readyAttack1) {
            playerComponent.timeAttack += deltaTime;
            if (playerComponent.timeAttack > timeReload) {
                playerComponent.timeAttack = 0;
                readyAttack1 = true;
            }
        }


        if((isAttack1  || isAttack2) && readyAttack1) {
            playerComponent.stop = true;
            Vector2 position = box2DComponent.body.getPosition();

            game.ecsEngine.createDamageArea(new DamageArea(position, playerComponent.direction,
                game.BaseSize, game.BaseSize, damage, speed, range, true) );
            readyAttack1 = false;
        }

        if(playerComponent.stop){
            time += deltaTime;
            if(time > 0.5f){
                playerComponent.stop = false;
                time = 0;
            }
        }
        isAttack1 = isAttack2 = false;

    }

    public void Input(){
        if(game.keyHandler.isAttack1) {
            isAttack1 = true;
            damage = 10;
            speed = 300 * Main.PPM;
            range = 8 * Main.PPM;
        }
        if(game.keyHandler.isAttack2) {
            isAttack2 = true;
            speed = 200 * Main.PPM;
            range = 160 * Main.PPM;
            damage = 10;
        }
    }
}
