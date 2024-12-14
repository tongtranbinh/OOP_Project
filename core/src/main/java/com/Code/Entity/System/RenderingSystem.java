package com.Code.Entity.System;


import com.Code.Controller.KeyHandler;
import com.Code.Entity.Component.*;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.BossAnimation;
import com.Code.Others.DirectionType;
import com.Code.Scenes.Hud;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class RenderingSystem {
    Main game;
    SpriteBatch batch;
    BossAnimation bossAnimation;
    Hud hud;
    public float Statetime = 0;
    public float playerStatetime = 0;
    public float dStatetime = 0;
    int[] animationDownfixX = {15, 12, 0, 0, -3};
    int[] animationDownfixY = {12, 18, 15, 18, 15};
    int[] animationLeftfixX = {3, 9, 12, 9, 9};
    int[] animationLeftfixY = {6, 3, 0, 0, 0};
    int[] animationRightfixX = {-9, -12, -12, -15, -15};
    int[] animationRightfixY = {6, 6, 0, -3, -3};
    int[] animationUpfixX = {-12, -12, -6, 0, 0};
    int[] animationUpfixY = {-3, -6, -9, -6, -3};
    int fixX;
    int fixY;
    KeyHandler keyHandler;



    public Texture currentTexture = null;
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();



    public RenderingSystem(Main game) {
        this.game = game;
        this.batch = game.batch;
        bossAnimation = new BossAnimation();
        keyHandler = game.keyHandler;

    }
    int state = 0;
    int laststate = 0;
    int dstate = 0;
    int lastdstate = 0;
    // stand = 0 a1 = 1 a2 = 2 run = 3
    public void render(float delta){
        playerStatetime = (state == laststate) ? playerStatetime + delta : 0;
        laststate = state;

        dStatetime = (dstate == lastdstate) ? dStatetime + delta : 0;
        lastdstate = dstate;

        Statetime += delta;
        batch.begin();
        renderDamagedArea(delta);
        renderBoss(delta);
        renderEnemy(delta);
        renderPlayer(delta);
        batch.end();
    }


    public void renderPlayer(float delta){
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(game.ecsEngine.playerEntity);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(game.ecsEngine.playerEntity);
        Vector2 speed = box2DComponent.body.getLinearVelocity();
        int dst = (int) speed.dst(new Vector2(0,0));
        int frameIndex = 0;
        if(playerComponent.stop){
            if(playerComponent.isAttacking1) {
                state = 1;
                Sprite attack = new Sprite();
                Sprite sword = new Sprite();
                switch (playerComponent.direction){
                    case UP:{
                        frameIndex = bossAnimation.SwordUp.getKeyFrameIndex(playerStatetime);
                        fixX = animationUpfixX[frameIndex];
                        fixY = animationUpfixY[frameIndex];
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashUp));
                        sword = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SwordUp));
                        break;
                    }
                    case DOWN:{
                        frameIndex = bossAnimation.SwordDown.getKeyFrameIndex(playerStatetime);
                        fixX = animationDownfixX[frameIndex];
                        fixY = animationDownfixY[frameIndex];
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashDown));
                        sword = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SwordDown));
                        break;
                    }
                    case LEFT:{
                        frameIndex = bossAnimation.SwordLeft.getKeyFrameIndex(playerStatetime);
                        fixX = animationLeftfixX[frameIndex];
                        fixY = animationLeftfixY[frameIndex];
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashLeft));
                        sword = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SwordLeft));
                        break;
                    }
                    case RIGHT:{
                        frameIndex = bossAnimation.SwordRight.getKeyFrameIndex(playerStatetime);
                        fixX = animationRightfixX[frameIndex];
                        fixY = animationRightfixY[frameIndex];
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashRight));
                        sword = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SwordRight));
                        break;
                    }
                }
                Vector2 pos = box2DComponent.body.getPosition();
                attack.setBounds(pos.x - game.BaseSize * 1.5f, pos.y - game.BaseSize * 1.5f, game.BaseSize * 3, game.BaseSize * 3);
                sword.setBounds(pos.x - game.BaseSize * 1.5f - fixX * Main.PPM, pos.y - game.BaseSize * 1.5f - fixY * Main.PPM, game.BaseSize * 3, game.BaseSize * 3);
                if(playerComponent.direction == DirectionType.UP){
                    sword.draw(batch);
                    attack.draw(batch);

                } else {
                    attack.draw(batch);
                    sword.draw(batch);
                }
            }
            else {
                state = 2;
                Sprite attack = new Sprite();
                switch (playerComponent.direction){
                    case UP:{
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashUp));
                        break;
                    }
                    case DOWN:{
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashDown));

                        break;
                    }
                    case LEFT:{

                            attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashLeft));
                        break;
                    }
                    case RIGHT:{
                        attack = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.SlashRight));
                        break;
                    }
                }
                Vector2 pos = box2DComponent.body.getPosition();
                attack.setBounds(pos.x - game.BaseSize * 1.5f, pos.y - game.BaseSize * 1.5f, game.BaseSize * 3, game.BaseSize * 3);
                attack.draw(batch);
            }
        }
        else {
            if(dst == 0) state = 0;
            else state = 3;
            Sprite walk = new Sprite();
            switch (playerComponent.direction){
                case UP:{
                    if(dst == 0)
                        walk = new Sprite( bossAnimation.StandUp );
                    else
                        walk = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.WalkUp));
                    break;
                }
                case DOWN:{
                    if(dst == 0)
                        walk = new Sprite( bossAnimation.StandDown );
                    else
                        walk = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.WalkDown));
                    break;
                }
                case LEFT:{
                    if(dst == 0)
                        walk = new Sprite( bossAnimation.StandLeft );
                    else
                        walk = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.WalkLeft));
                    break;
                }
                case RIGHT:{
                    if(dst == 0)
                        walk = new Sprite( bossAnimation.StandRight );
                    else
                        walk = new Sprite( bossAnimation.getframes(playerStatetime,bossAnimation.WalkRight));
                    break;
                }
            }
            Vector2 pos = box2DComponent.body.getPosition();
            walk.setBounds(pos.x - game.BaseSize * 1.5f, pos.y - game.BaseSize * 1.5f, game.BaseSize * 3, game.BaseSize * 3);
            walk.draw(batch);
        }
    }

    public void renderBoss(float delta){
        Family family = Family.all(BossComponent.class).get();
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(family);
        for(Entity entity : entities){
            BossComponent bossComponent = ECSEngine.bossComponentMapper.get(entity);
            Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
            Vector2 pos = box2DComponent.body.getPosition();

            Sprite bossSprite = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.BossStand));
            bossSprite.setBounds(pos.x - game.BaseSize * 3.6f, pos.y - game.BaseSize * 3.6f, game.BaseSize * 7.2f, game.BaseSize * 7.2f);
            bossSprite.draw(batch);

            Sprite effect = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.Effect));
            effect.setBounds(pos.x - game.BaseSize * 4, pos.y - game.BaseSize * 4, game.BaseSize * 8, game.BaseSize * 8);
            effect.draw(batch);


        }
    }

    public void renderDamagedArea(float delta){
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(game.ecsEngine.playerEntity);
        Family family = Family.all(DamageAreaComponent.class).get();
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(family);
        for(Entity entity : entities){
            Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
            DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(entity);
            Vector2 pos = box2DComponent.body.getPosition();
            Sprite bullets;
            if(damageAreaComponent.owner == 4)  {
                bullets = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.Bullets));
                bullets.setBounds(pos.x - game.BaseSize * 0.75f, pos.y - game.BaseSize * 0.75f, game.BaseSize * 1.5f, game.BaseSize * 1.5f);

            }
            else {
                if(damageAreaComponent.type == 1) {
                    bullets = new Sprite(bossAnimation.getframes(dStatetime, bossAnimation.Sword));
                    dstate = 0;
                    bullets.setBounds(pos.x - 14 * Main.PPM, pos.y - 32 * Main.PPM, 28 * Main.PPM, 64 * Main.PPM);
                    switch (playerComponent.direction){
                        case UP:{
                            bullets.setOrigin(bullets.getWidth()/2,bullets.getHeight()/2);
                            bullets.setRotation(90);
                            break;
                        }
                        case DOWN:{
                            bullets.setOrigin(bullets.getWidth()/2,bullets.getHeight()/2);
                            bullets.setRotation(270);
                            break;
                        }
                        case LEFT:{
                            bullets.setFlip(true, false);
                            break;

                        }
                        case RIGHT:{

                        }
                    }
                }
                    else {
                        bullets = new Sprite(bossAnimation.getframes(dStatetime, bossAnimation.PlayerBullets));
                        dstate = 1;
                        bullets.setBounds(pos.x - game.BaseSize * 0.75f, pos.y - game.BaseSize * 0.75f, game.BaseSize * 1.5f, game.BaseSize * 1.5f);
                }
            }
            bullets.draw(batch);
        }
    }

    public void renderEnemy(float delta) {
        Family family = Family.all(EnemyComponent.class).get();
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(family);
        for (Entity entity : entities) {
            Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
            Vector2 pos = box2DComponent.body.getPosition();
            Sprite enemy = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.Enemy));;
            enemy.setBounds(pos.x - game.BaseSize * 1.5f, pos.y - game.BaseSize * 1.5f, game.BaseSize * 3f, game.BaseSize * 3f);
            enemy.draw(batch);
        }
    }
    public void dispose() {
        for (Texture texture : down) {
            texture.dispose();
        }
        for (Texture texture : up) {
            texture.dispose();
        }
        for (Texture texture : left) {
            texture.dispose();
        }
        for (Texture texture : right) {
            texture.dispose();
        }
    }
}
