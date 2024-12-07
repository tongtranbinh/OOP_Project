package com.Code.Entity.System;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.sqrt;

public class PlayerMovementSystem extends IteratingSystem {
    Main game;

    float move_x;
    float move_y;
    DirectionType direction = DirectionType.DOWN    ;


    public PlayerMovementSystem(Main game) {
        super(Family.all(PlayerComponent.class, Box2DComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(entity);
        final Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);

        move_x = move_y = 0;
        this.Input();

        float move = (float) sqrt(move_x * move_x + move_y * move_y);
        if(playerComponent.stop) move = 0;


        if (move != 0) {
            float speedx = move_x * playerComponent.speed / move;
            float speedy = move_y * playerComponent.speed / move;
            box2DComponent.body.applyLinearImpulse(speedx - box2DComponent.body.getLinearVelocity().x,
                speedy - box2DComponent.body.getLinearVelocity().y ,
                box2DComponent.body.getWorldCenter().x,
                box2DComponent.body.getWorldCenter().y, true);
        }
        else{
            box2DComponent.body.setLinearVelocity(new Vector2(0,0));
        }

        playerComponent.direction = direction;
    }

    public void WalkUp(){
        move_y = 1 ;
        this.direction = DirectionType.UP;
    }
    public void WalkLeft(){
        move_x = -1 ;
        this.direction = DirectionType.LEFT;
    }
    public void WalkDown(){
        move_y = -1 ;
        this.direction = DirectionType.DOWN;
    }
    public void WalkRight(){
        move_x = 1 ;
        this.direction = DirectionType.RIGHT;
    }

    public void Input(){

        if(game.keyHandler.up) WalkUp();
        if(game.keyHandler.left) WalkLeft();
        if(game.keyHandler.down) WalkDown();
        if(game.keyHandler.right) WalkRight();

    }
}
