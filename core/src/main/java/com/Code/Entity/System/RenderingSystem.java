package com.Code.Entity.System;


import com.Code.Entity.Component.*;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.BossAnimation;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class RenderingSystem {
    Main game;
    SpriteBatch batch;
    BossAnimation bossAnimation;
    public float Statetime = 0;
    public Texture currentTexture = null;
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();
    private float stateTime = 0f; // Thời gian trạng thái để điều chỉnh animation



    public RenderingSystem(Main game) {
        this.game = game;
        this.batch = game.batch;
        bossAnimation = new BossAnimation();
        CreatePlayerAnimation();
    }

    public void render(float delta){
        Statetime += delta;
        batch.begin();
        renderBoss(delta);
        renderPLayer(delta);
        renderDamagedArea(delta);
        renderEnemy(delta);
        batch.end();
    }

    // Đọc các sprite (hình ảnh) của Player cho các hướng khác nhau
    public void CreatePlayerAnimation() {
        // Tải các sprite cho hướng "Down"
        for (int i = 0; i <= 11; i++) {
            String path = "Player/Walk/Walk Down-split/imageonline/" + i + "0.png";
            Texture sprite = new Texture(path);
            down.add(sprite);
        }

        // Tải các sprite cho hướng "Up"
        for (int i = 0; i <= 11; i++) {
            String path = "Player/Walk/Walk Up-split/imageonline/" + i + "0.png";
            Texture sprite = new Texture(path);
            up.add(sprite);
        }

        // Tải các sprite cho hướng "Right"
        for (int i = 0; i <= 11; i++) {
            String path = "Player/Walk/Walk Right-split/imageonline/" + i + "0.png";
            Texture sprite = new Texture(path);
            right.add(sprite);
        }

        // Tải các sprite cho hướng "Left"
        for (int i = 0; i <= 11; i++) {
            String path = "Player/Walk/Walk Left-split/imageonline/" + i + "0.png";
            Texture sprite = new Texture(path);
            left.add(sprite);
        }
    }

    // Render Player với animation và cập nhật frame theo delta time
    public void renderPLayer(float delta) {

        Entity entity = game.ecsEngine.playerEntity;
        // Lấy thông tin về PlayerComponent
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(entity);

        // Cập nhật thời gian để điều chỉnh frame của animation
        stateTime += delta;  // Cộng deltaTime vào stateTime để tạo chuyển động mượt mà

        // Tính toán chỉ số frame hiện tại
        int frameIndex = (int)(stateTime * 10) % 12;  // Mỗi sprite hiện trong 0.1 giây, có 12 frame

        // Chọn danh sách sprites dựa trên hướng di chuyển của Player
        switch (playerComponent.direction) {
            case DOWN:
                currentTexture = down.get(frameIndex);
                break;
            case UP:
                currentTexture = up.get(frameIndex);
                break;
            case LEFT:
                currentTexture = left.get(frameIndex);
                break;
            case RIGHT:
                currentTexture = right.get(frameIndex);
                break;
        }

        // Vẽ sprite lên màn hình tại vị trí của Player

        // Lấy vị trí của Player từ Box2DComponent
        Box2DComponent box2DComponent = entity.getComponent(Box2DComponent.class);
        Vector2 position = box2DComponent.body.getPosition();

        batch.draw(currentTexture, position.x - game.BaseSize * 1.5f , position.y - game.BaseSize * 1.5f, game.BaseSize * 3 , game.BaseSize * 3);
    }

    public void renderBoss(float delta){
        Family family = Family.all(BossComponent.class).get();
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(family);
        for(Entity entity : entities){
            BossComponent bossComponent = ECSEngine.bossComponentMapper.get(entity);
            Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
            Vector2 pos = box2DComponent.body.getPosition();

            Sprite bossSprite = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.BossStand));
            bossSprite.setBounds(pos.x - game.BaseSize * 3, pos.y - game.BaseSize * 3, game.BaseSize * 6, game.BaseSize * 6);
            bossSprite.draw(batch);

            Sprite effect = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.Effect));
            effect.setBounds(pos.x - game.BaseSize * 3, pos.y - game.BaseSize * 3, game.BaseSize * 6, game.BaseSize * 6);
            effect.draw(batch);


        }
    }

    public void renderDamagedArea(float delta){
        Family family = Family.all(DamageAreaComponent.class).get();
        ImmutableArray<Entity> entities = game.ecsEngine.getEntitiesFor(family);
        for(Entity entity : entities){
            Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
            DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(entity);
            Vector2 pos = box2DComponent.body.getPosition();
            Sprite bullets;
            if(damageAreaComponent.owner == 4)  bullets = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.Bullets));
            else bullets = new Sprite(bossAnimation.getframes(Statetime, bossAnimation.PlayerBullets));
            bullets.setBounds(pos.x - game.BaseSize * 0.75f, pos.y - game.BaseSize * 0.75f, game.BaseSize * 1.5f, game.BaseSize * 1.5f);
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

