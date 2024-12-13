package com.Code.Entity.Component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.ashley.core.Component;

import java.util.ArrayList;

public class PlayerAnimation implements Component {
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();
    Texture Sprites;

    public void CreatePlayerAnimation()
    {
        for(int i = 0; i <= 11 ; i++)
        {
            String path = "Player/Walk/Walk Down-split/imageonline/"+ i +"0.png";
            Sprites = new Texture(path);
            down.add(Sprites);
        }
        for(int i = 0; i <= 11 ; i++)
        {
            String path = "Player/Walk/Walk Up-split/imageonline/"+ i +"0.png";
            Sprites = new Texture(path);
            up.add(Sprites);
        }
        for(int i = 0; i <= 11 ; i++)
        {
            String path = "Player/Walk/Walk Right-split/imageonline/"+ i +"0.png";
            Sprites = new Texture(path);
            right.add(Sprites);
        }
        for(int i = 0; i <= 11 ; i++)
        {
            String path = "Player/Walk/Walk Left-split/imageonline/"+ i +"0.png";
            Sprites = new Texture(path);
            left.add(Sprites);
        }
    }
}
