package com.Code.Animation;


import com.Code.Entity.Player;
import com.Code.Image.PlayerImage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class PlayerAnimation {
    PlayerImage playerImage = new PlayerImage();
    Player player = new Player(0,0);
    public Texture Sprites = playerImage.up.get(0);


    public void CreatePlayerAnimation(){

        if(player.direction == 0){
            Sprites = playerImage.up.get(0);
        }
        if(player.direction == 1){
            Sprites = playerImage.left.get(0);
        }
        if(player.direction == 2){
            Sprites = playerImage.down.get(0);
        }
        if(player.direction == 3){
            Sprites = playerImage.right.get(0);
        }
    }

}

