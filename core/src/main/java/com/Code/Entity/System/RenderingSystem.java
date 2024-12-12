package com.Code.Entity.System;


import com.Code.Main;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderingSystem  {
    Main game;
    SpriteBatch batch;
    public RenderingSystem(Main game){
        this.game = game;
        this.batch = game.batch;
    }

    public void render(float delta){

    }
}
