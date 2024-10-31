package com.Code.Entity;

import com.Code.Animation.PlayerAnimation;
import com.Code.Controller.KeyHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


public class Player {
    public int speed;
    public float x,y;

    public int isAttack,direction;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
        speed = 3;
        direction = 2;
    }

    public void update(){

    }
    // direction WASD = 0123
}
