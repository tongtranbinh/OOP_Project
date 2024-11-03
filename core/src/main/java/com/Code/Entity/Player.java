package com.Code.Entity;

import com.Code.Animation.PlayerAnimation;
import com.Code.Controller.KeyHandler;
import com.Code.Controller.PlayerController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


public class Player {
    public int speed;
    public float x,y;

    public int isAttack,direction;
    PlayerController playerController;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
        speed = 3;
        direction = 2;
        playerController = new PlayerController(this);
    }

    public void update(){
        Vector2 updatePlayerValue;
        updatePlayerValue = playerController.updatePlayer();
        this.x += updatePlayerValue.x;
        this.y += updatePlayerValue.y;
    }
    // direction WASD = 0123
}
