package com.Code.Entity.System;


import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;

import com.Code.Entity.ECSEngine;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;



public class RenderingSystem {
    Main game;
    SpriteBatch batch;
    public Texture currentTexture = null;
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();
    private float stateTime = 0f; // Thời gian trạng thái để điều chỉnh animation



    public RenderingSystem(Main game) {
        this.game = game;
        this.batch = game.batch;
    }

    public void render(float delta){
        batch.begin();
        renderPLayer(delta);
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
        CreatePlayerAnimation();
        for (Entity entity : game.ecsEngine.PlayerEntityArray) {
            // Lấy thông tin về PlayerComponent
            PlayerComponent playerComponent = game.ecsEngine.playerComponentMapper.get(entity);

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

            batch.draw(currentTexture,position.x, position.y , 3f, 3f);

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

