package com.Code.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BossAnimation extends Sprite {
    public Texture texture;
    Animation<TextureRegion> BossStand;
    Animation<TextureRegion> BossAttack;
    Array<TextureRegion> frames = new Array<TextureRegion>();
    public BossAnimation(){
        texture = new Texture("Boss/Crystal Knight.png");
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));

        }
        BossStand = new Animation<TextureRegion>(0.5f, frames);
        frames.clear();
        texture = new Texture("Boss/BossAttack.png");
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(texture,i * 64 ,0,64,64));
        }
        BossAttack = new Animation<TextureRegion>(0.5f, frames);

    }

    public TextureRegion getframes(float delta){
        TextureRegion region;
        region = BossAttack.getKeyFrame(delta, true);
        return region;
    }

}
