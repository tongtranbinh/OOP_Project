package com.Code.Animation;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class PlayerAnimation {
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();
    public ArrayList<Texture> SlashUp = new ArrayList<Texture>();
    public ArrayList<Texture> SlashDown = new ArrayList<Texture>();
    public ArrayList<Texture> SlashRight = new ArrayList<Texture>();
    public ArrayList<Texture> SlashLeft = new ArrayList<Texture>();
    public ArrayList<Texture> SwordUp = new ArrayList<Texture>();
    public ArrayList<Texture> SwordDown = new ArrayList<Texture>();
    public ArrayList<Texture> SwordRight = new ArrayList<Texture>();
    public ArrayList<Texture> SwordLeft = new ArrayList<Texture>();

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

        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Slash Up-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SlashUp.add(Sprites);
        }
        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Slash Left-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SlashLeft.add(Sprites);
        }
        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Slash Right-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SlashRight.add(Sprites);
        }

        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Sword Down-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SwordDown.add(Sprites);
        }
        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Sword Up-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SwordUp.add(Sprites);
        }
        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Sword Left-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SwordLeft.add(Sprites);
        }
        for(int i = 0; i <= 4 ; i++)
        {
            String path = "Player/Attack/Sword Right-split/imageonline/" + i + "0.png";
            Sprites = new Texture(path);
            SwordRight.add(Sprites);
        }


    }
}

