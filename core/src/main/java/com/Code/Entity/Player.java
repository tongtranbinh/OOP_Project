package com.Code.Entity;

import com.Code.Animation.PlayerAnimation;
import com.Code.Controller.KeyHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
    public int speed, cnt = 0, frame = 0, isMove = 0;
    public float x,y;
    public Vector2 move = new Vector2(0,0);
    public int direction = 2,lastDirection = 2;
    public PlayerAnimation Animation = new PlayerAnimation();
    public KeyHandler keyHandler = new KeyHandler();
    public Texture Image;


    public void setDefaultValue() {
        x = 100;
        y = 100;
        speed = 3;
        Animation.CreatePlayerAnimation();
    }

    public void dispose()
    {
        Animation.up.get(0).dispose();
    }

    // direction WASD = 0123
    public void update()
    {
        isMove = 0;
        move = new Vector2(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            move.y += 1;
            isMove = 1;
            direction = 0;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            move.x -= 1;
            isMove = 1;
            direction = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            move.y -= 1;
            isMove = 1;
            direction = 2;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            move.x += 1;
            isMove = 1;
            direction = 3;
        }
        if(direction != lastDirection) {
            cnt = 0;
            lastDirection = direction;
        }
        if(frame >= 7) {
            frame = 0;
            cnt++;
            cnt%=11;
        }
        frame++;
        if(isMove != 1) {
            cnt = 0;
            frame = 0;
        }
        if(direction == 0) Image = Animation.up.get(cnt);
        if(direction == 1) Image = Animation.left.get(cnt);
        if(direction == 2) Image = Animation.down.get(cnt);
        if(direction == 3) Image = Animation.right.get(cnt);

        if(move.x != 0 && move.y != 0){
            x += move.x * speed * 0.7;
            y += move.y * speed * 0.7;
        } else {
            x += move.x * speed ;
            y += move.y * speed ;
        }
    }
}
