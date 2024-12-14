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
    public Animation<TextureRegion> Enemy;

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

        texture = new Texture("Boss/mm-crawl.png");
        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));
        }
        Enemy = new Animation<TextureRegion>(0.5f, frames);
        frames.clear();
    }

    public TextureRegion getframes(float delta,Animation<TextureRegion> animation){
        TextureRegion region;
        region = animation.getKeyFrame(delta, true);
        return region;
    }
}
