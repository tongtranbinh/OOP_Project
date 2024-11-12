package com.Code.Animation;

import com.Code.Main;
import com.badlogic.gdx.graphics.Texture;


public class Animation {
    Main game;
    public PlayerAnimation playerAnimation;
    public Texture playerSprite;

    public Animation(Main game){
        this.game = game;
        playerAnimation = new PlayerAnimation(game);
        playerAnimation.CreatePlayerAnimation();


        playerSprite = playerAnimation.Sprites;
    }
}
