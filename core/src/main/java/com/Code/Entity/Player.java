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
        playerPosition = playerBox.playerObject.body.getPosition();
        speed = 100;
        direction = 2;
        playerController = new PlayerController(this);
    }

    public void update(){
        Vector2 updatePlayerValue;
        updatePlayerValue = playerController.updatePlayer();
        updatePlayerValue.x -= playerBox.playerObject.body.getLinearVelocity().x;
        updatePlayerValue.y -= playerBox.playerObject.body.getLinearVelocity().y;
        playerBox.playerObject.body.applyLinearImpulse(updatePlayerValue,playerBox.playerObject.body.getWorldCenter(),true);
        playerPosition = playerBox.playerObject.body.getPosition();
    }
    // direction WASD = 0123
}
