package com.Code.Controller;

import com.Code.Entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.sqrt;

public class PlayerController {
    public KeyHandler keyHandler = new KeyHandler();
    public Player player;
    float move_x,move_y;
    int frame = 0;

    public PlayerController(Player player){
        this.player = player;
        move_x = move_y = 0;
    }
    public void WalkUp(){
        move_y = 1 ;
    }
    public void WalkLeft(){
        move_x = -1 ;
    }
    public void WalkDown(){
        move_y = -1 ;
    }
    public void WalkRight(){
        move_x = 1 ;
    }

    public void Input(){

        if(keyHandler.up) WalkUp();
        if(keyHandler.left) WalkLeft();
        if(keyHandler.down) WalkDown();
        if(keyHandler.right) WalkRight();

    }

    public Vector2 updatePlayer(){
        Gdx.input.setInputProcessor(keyHandler);
        move_x = move_y = frame = 0;
        this.Input();
        Vector2 updatePlayerValue = new Vector2();
        float move = (float) sqrt(move_x * move_x + move_y * move_y);
        if(move != 0){
            updatePlayerValue.x += move_x * player.speed / move;
            updatePlayerValue.y += move_y * player.speed / move;
        }

        return updatePlayerValue;
    }
}
