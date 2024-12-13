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

    }

    public TextureRegion getframes(float delta,Animation<TextureRegion> animation){
        TextureRegion region;
        region = animation.getKeyFrame(delta, true);
        return region;
    }

}
