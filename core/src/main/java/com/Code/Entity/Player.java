package com.Code.Entity;

import com.Code.Animation.PlayerAnimation;
import com.Code.Box2D.Box2Dobject;
import com.Code.Controller.KeyHandler;
import com.Code.Controller.PlayerController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


public class Player {
    public int speed;
    public Vector2 playerPosition;

    public int isAttack,direction;
    PlayerController playerController;
    Box2Dobject playerBox;

    public Player(Box2Dobject playerBox)
    {
        this.playerBox = playerBox;
        playerPosition = playerBox.playerBody.getPosition();
        speed = 50;
        direction = 2;
        playerController = new PlayerController(this);
    }

    public void update(){
        Vector2 updatePlayerValue;
        updatePlayerValue = playerController.updatePlayer();
        updatePlayerValue.x -= playerBox.playerBody.getLinearVelocity().x;
        updatePlayerValue.y -= playerBox.playerBody.getLinearVelocity().y;
        playerBox.playerBody.applyLinearImpulse(updatePlayerValue,playerBox.playerBody.getWorldCenter(),true);
        playerPosition = playerBox.playerBody.getPosition();
        System.out.println(playerBox.playerBody.getLinearVelocity());
    }
    // direction WASD = 0123
}
