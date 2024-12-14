package com.Code.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BossAnimation extends Sprite {
    public Texture texture;
    public Animation<TextureRegion> BossStand;
    public Animation<TextureRegion> Effect;
    public Animation<TextureRegion> BossDamaged;
    public Animation<TextureRegion> Bullets;
    public Animation<TextureRegion> PlayerBullets;
    public Animation<TextureRegion> Sword;
    public Animation<TextureRegion> Enemy;
    public Animation<TextureRegion> WalkUp;
    public Animation<TextureRegion> WalkDown;
    public Animation<TextureRegion> WalkRight;
    public Animation<TextureRegion> WalkLeft;
    public Animation<TextureRegion> SlashUp;
    public Animation<TextureRegion> SlashDown;
    public Animation<TextureRegion> SlashRight;
    public Animation<TextureRegion> SlashLeft;
    public Animation<TextureRegion> SwordUp;
    public Animation<TextureRegion> SwordRight;
    public Animation<TextureRegion> SwordDown;
    public Animation<TextureRegion> SwordLeft;
    public TextureRegion StandUp;
    public TextureRegion StandRight;
    public TextureRegion StandDown;
    public TextureRegion StandLeft;


    Array<TextureRegion> frames = new Array<TextureRegion>();
    public BossAnimation(){
        texture = new Texture("Boss/Crystal Knight.png");
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));

        }
        BossStand = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();


        texture = new Texture("Boss/effect.png");
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));
        }
        Effect = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        texture = new Texture("Boss/BossDamaged.png");
        for(int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture,0 ,0,64,64));
        }

        BossDamaged = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        texture = new Texture("Boss/Effect and Bullet 16x16/bullet.png");
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(texture,i * 16 ,0,16,16));
        }
        Bullets = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        texture = new Texture("Boss/Effect and Bullet 16x16/PlayerBullets.png");
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(texture,i * 16 ,0,16,16));
        }
        PlayerBullets = new Animation<TextureRegion>(0.05f, frames);
        frames.clear();

        texture = new Texture("Boss/Effect and Bullet 16x16/Fire Effect and Bullet 16x16.png");
        for(int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(texture, 28 ,0,14,32));
        }
        Sword = new Animation<TextureRegion>(0.06666f, frames);
        frames.clear();

        texture = new Texture("Boss/mm-crawl.png");
        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));
        }
        Enemy = new Animation<TextureRegion>(0.5f, frames);
        frames.clear();

// Walk Up
        texture = new Texture("Player/Walk Up.png");
        StandUp = new TextureRegion(texture, 0, 0, 24, 24);
        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        WalkUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Player Down
        texture = new Texture("Player/Walk Down.png");
        StandDown = new TextureRegion(texture, 0, 0, 24, 24);
        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        WalkDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Player Right
        texture = new Texture("Player/Walk Right.png");
        StandRight= new TextureRegion(texture, 0, 0, 24, 24);

        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        WalkRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Player Left
        texture = new Texture("Player/Walk Left.png");
        StandLeft = new TextureRegion(texture, 0, 0, 24, 24);

        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        WalkLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Slash Up
        texture = new Texture("Player/Slash Up.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SlashUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Slash Down
        texture = new Texture("Player/Slash Down.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SlashDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Slash Right
        texture = new Texture("Player/Slash Right.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SlashRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Slash Left
        texture = new Texture("Player/Slash Left.png");
        for (int i = 4; i >= 0; i--) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SlashLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Sword Up
        texture = new Texture("Player/Sword Up.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SwordUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Sword Right
        texture = new Texture("Player/Sword Right.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SwordRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Sword Down
        texture = new Texture("Player/Sword Down.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SwordDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

// Sword Left
        texture = new Texture("Player/Sword Left.png");
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 24, 0, 24, 24));
        }
        SwordLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

    }

    public TextureRegion getframes(float delta,Animation<TextureRegion> animation){
        TextureRegion region;
        region = animation.getKeyFrame(delta, true);
        return region;
    }
}
