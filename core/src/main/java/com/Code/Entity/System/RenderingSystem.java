package com.Code.Entity.System;

import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.Component.PlayerAnimation;
import com.Code.Entity.ECSEngine;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
public class RenderingSystem extends IteratingSystem {
    private float stateTime = 0f; // Thời gian trạng thái để điều chỉnh animation
    public Texture currentFrame = null;
    public SpriteBatch spriteBatch;
    Main game;
    public RenderingSystem(Main gane) {
        super(Family.all(PlayerComponent.class, Box2DComponent.class, PlayerAnimation.class).get());
        this.game = gane;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // Lấy các thành phần
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(entity);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
        PlayerAnimation playerAnimation = ECSEngine.playerAnimationMapper.get(entity);

        // Cập nhật thời gian delta
        stateTime += deltaTime;

        // Lấy hướng di chuyển của nhân vật
        DirectionType direction = playerComponent.direction;

        // Chọn frame phù hợp dựa trên hướng

        int frameIndex = (int) ((stateTime * 10) % 12); // 12 là số lượng frame trong mỗi hướng

        switch (direction) {
            case UP:
                currentFrame = playerAnimation.up.get(frameIndex);
                break;
            case DOWN:
                currentFrame = playerAnimation.down.get(frameIndex);
                break;
            case LEFT:
                currentFrame = playerAnimation.left.get(frameIndex);
                break;
            case RIGHT:
                currentFrame = playerAnimation.right.get(frameIndex);
                break;
        }

        // Vẽ sprite lên màn hình
        if (currentFrame != null) {
            System.out.println("run");

            game.batch.begin();
            game.batch.draw(
                currentFrame,
                box2DComponent.body.getPosition().x - 0.5f,
                box2DComponent.body.getPosition().y - 0.5f,
                1, 1 // Kích thước (width, height)
            );
            game.batch.end();
        }
    }
}
