package com.Code.Animation;

import com.badlogic.gdx.graphics.Texture;


public class Animation {
    public PlayerAnimation playerAnimation = new PlayerAnimation();
    public Texture playerSprite;

    public Animation(){
        playerAnimation.CreatePlayerAnimation();


        playerSprite = playerAnimation.Sprites;
    }
}
