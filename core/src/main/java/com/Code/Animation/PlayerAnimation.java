package com.Code.Animation;


import com.Code.Entity.Player;
import com.Code.Image.PlayerImage;
import com.Code.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class PlayerAnimation {
    Main game;
    PlayerImage playerImage = new PlayerImage();
    public Texture Sprites = playerImage.up.get(0);
    public PlayerAnimation(Main game){
        this.game = game;
    }

    public void CreatePlayerAnimation(){

        if(game.player.direction == 0){
            Sprites = playerImage.up.get(0);
        }
        if(game.player.direction == 1){
            Sprites = playerImage.left.get(0);
        }
        if(game.player.direction == 2){
            Sprites = playerImage.down.get(0);
        }
        if(game.player.direction == 3){
            Sprites = playerImage.right.get(0);
        }
    }

}

