package com.Code.Entity.System;

import com.Code.Others.DirectionType;
import com.Code.Effect.DamageArea;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.System.PlayerAttackSystem;
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
    public ArrayList<Texture> idleUp = new ArrayList<>();
    public ArrayList<Texture> idleDown = new ArrayList<>();
    public ArrayList<Texture> idleLeft = new ArrayList<>();
    public ArrayList<Texture> idleRight = new ArrayList<>();
    public ArrayList<Texture> SlashUp = new ArrayList<>();
    public ArrayList<Texture> SlashDown = new ArrayList<>();
    public ArrayList<Texture> SlashLeft = new ArrayList<>();
    public ArrayList<Texture> SlashRight = new ArrayList<>();
    public ArrayList<Texture> swordUp = new ArrayList<>();
    public ArrayList<Texture> swordDown = new ArrayList<>();
    public ArrayList<Texture> swordLeft = new ArrayList<>();
    public ArrayList<Texture> swordRight = new ArrayList<>();
    public float stateTime = 0f; // Thời gian trạng thái để điều chỉnh animation
    public float attackTime = 0f;

    private boolean isAttacking = false;


    public RenderingSystem(Main game) {
        this.game = game;
        this.batch = game.batch;
        CreatePlayerAnimation();
        loadFightAnimations();
        loadSwordAnimations();
    }

    public void render(float delta){
        batch.begin();

            renderPLayer(delta);
            handlePlayerAttack(delta);
            renderFightAnimation(delta);

        batch.end();


    }
    public void loadFightAnimations() {
        for (int i = 0; i <= 4; i++) {
            SlashDown.add(new Texture("Player/Attack/Slash Down-Split/imageonline/" + i + "0.png"));
            SlashUp.add(new Texture("Player/Attack/Slash Up-Split/imageonline/" + i + "0.png"));
            SlashLeft.add(new Texture("Player/Attack/Slash Left-Split/imageonline/" + i + "0.png"));
            SlashRight.add(new Texture("Player/Attack/Slash Right-Split/imageonline/-" + i + "0.png"));
        }
    }
    public void loadSwordAnimations() {
        for (int i = 0; i <= 4; i++) {
            swordDown.add(new Texture("Player/Attack/Sword Down-Split/imageonline/" + i + "0.png"));
            swordUp.add(new Texture("Player/Attack/Sword Up-Split/imageonline/" + i + "0.png"));
            swordLeft.add(new Texture("Player/Attack/Sword Left-Split/imageonline/" + i + "0.png"));
            swordRight.add(new Texture("Player/Attack/Sword Right-Split/imageonline/" + i + "0.png"));
        }
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
        idleDown.add(new Texture("Player/Walk/Walk Down-split/imageonline/00.png"));
        idleUp.add(new Texture("Player/Walk/Walk Up-split/imageonline/00.png"));
        idleLeft.add(new Texture("Player/Walk/Walk Left-split/imageonline/00.png"));
        idleRight.add(new Texture("Player/Walk/Walk Right-split/imageonline/00.png"));
    }

    // Render Player với animation và cập nhật frame theo delta time
    public void renderPLayer(float delta) {

        for (Entity entity : game.ecsEngine.PlayerEntityArray) {
            // Lấy thông tin về PlayerComponent
            PlayerComponent playerComponent = game.ecsEngine.playerComponentMapper.get(entity);
            Box2DComponent box2DComponent = entity.getComponent(Box2DComponent.class);
            Vector2 position = box2DComponent.body.getPosition();
            // Kiểm tra nếu nhân vật không di chuyển
            if (!Gdx.input.isKeyPressed(Input.Keys.W) &&
                !Gdx.input.isKeyPressed(Input.Keys.S) &&
                !Gdx.input.isKeyPressed(Input.Keys.A) &&
                !Gdx.input.isKeyPressed(Input.Keys.D)) {

                // Hiển thị hoạt ảnh đứng yên tương ứng với hướng
                switch (playerComponent.direction) {
                    case DOWN:
                        currentTexture = idleDown.get(0);
                        break;
                    case UP:
                        currentTexture = idleUp.get(0);
                        break;
                    case LEFT:
                        currentTexture = idleLeft.get(0);
                        break;
                    case RIGHT:
                        currentTexture = idleRight.get(0);
                        break;

                }
                batch.draw(currentTexture, position.x - 1.5f * game.BaseSize, position.y - 1.5f * game.BaseSize, 3f, 3f);
            } else {

                // Cập nhật thời gian để điều chỉnh frame của animation
                stateTime += delta;  // Cộng deltaTime vào stateTime để tạo chuyển động mượt mà

                // Tính toán chỉ số frame hiện tại
                int frameIndex = (int) (stateTime * 10) % 12;  // Mỗi sprite hiện trong 0.1 giây, có 12 frame

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


                batch.draw(currentTexture, position.x - 1.5f * game.BaseSize, position.y - 1.5f * game.BaseSize, 3f, 3f);

            }
        }
    }
    public void handlePlayerAttack(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            isAttacking = true;


            stateTime = 0f; // Reset stateTime khi bắt đầu đánh
        }

        if (isAttacking) {
            renderFightAnimation(delta);
        }
    }

    public void renderFightAnimation(float delta) {

        if (!isAttacking) {
            return;
        }
        // Cập nhật thời gian để điều chỉnh frame của animation
        stateTime += delta;

        // Tính toán chỉ số frame hiện tại
        int frameIndex = (int) (stateTime * 10) % 5; // Mỗi frame hiển thị trong 0.1 giây
        for (Entity Entity : game.ecsEngine.PlayerEntityArray) {
            //Player Position Info
            Box2DComponent box2DComponent = Entity.getComponent(Box2DComponent.class);
            Vector2 position = box2DComponent.body.getPosition();
            // Lấy frame hiện tại dựa trên hướng và loại hoạt ảnh
            PlayerComponent playerComponent = game.ecsEngine.playerComponentMapper.get(Entity);
            switch (playerComponent.direction) {
                case DOWN:
                    currentTexture = swordDown.get(frameIndex);
                    break;
                case UP:
                    currentTexture = swordUp.get(frameIndex);
                    break;
                case LEFT:
                    currentTexture = swordLeft.get(frameIndex);
                    break;
                case RIGHT:
                    currentTexture = swordRight.get(frameIndex);
                    break;
            }


            if (playerComponent.direction == DirectionType.UP) {
                if (currentTexture != null) {
                    batch.draw(currentTexture,
                        position.x - 1.5f * game.BaseSize,
                        position.y - 1.0f * game.BaseSize,
                        3f, 3f);
                }
            }

            switch (playerComponent.direction) {
                case DOWN:
                    currentTexture = SlashDown.get(frameIndex);
                    break;
                case UP:
                    currentTexture = SlashUp.get(frameIndex);
                    break;
                case LEFT:
                    currentTexture = SlashLeft.get(frameIndex);
                    break;
                case RIGHT:
                    currentTexture = SlashRight.get(frameIndex);
                    break;
            }

            batch.draw(currentTexture, position.x - 1.5f * game.BaseSize, position.y - 1.5f * game.BaseSize, 3f, 3f);
            if (stateTime > 1.5f) { // Giả sử hoạt ảnh đánh kéo dài 0.5 giây
                isAttacking = false;
            }

            // Vẽ hoạt ảnh kiếm
            if (playerComponent.direction != DirectionType.UP) {
                switch (playerComponent.direction) {
                    case DOWN:
                        currentTexture = swordDown.get(frameIndex);
                        break;
                    case LEFT:
                        currentTexture = swordLeft.get(frameIndex);
                        break;
                    case RIGHT:
                        currentTexture = swordRight.get(frameIndex);
                        break;
                }

                if (currentTexture != null) {
                    switch (playerComponent.direction) {
                        case DOWN:
                            batch.draw(currentTexture,
                                position.x - 2.0f * game.BaseSize, // lệch trái
                                position.y - 2.0f * game.BaseSize, // Vẽ thấp hơn một chút
                                3f, 3f);
                            break;
                        case LEFT:
                            batch.draw(currentTexture,
                                position.x - 2.0f * game.BaseSize, // Vẽ lệch trái một chút
                                position.y - 1.5f * game.BaseSize, // Giữ nguyên vị trí Y
                                3f, 3f);
                            break;
                        case RIGHT:
                            batch.draw(currentTexture,
                                position.x - 1.0f * game.BaseSize, // Vẽ lệch phải một chút
                                position.y - 1.5f * game.BaseSize, // Giữ nguyên vị trí Y
                                3f, 3f);
                            break;
                    }
                }
            }
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
        for (Texture texture : SlashUp) {
            texture.dispose();
        }
        for (Texture texture : SlashDown) {
            texture.dispose();
        }
        for (Texture texture : SlashLeft) {
            texture.dispose();
        }
        for (Texture texture : SlashRight) {
            texture.dispose();
        }
        for (Texture texture : swordUp) {
            texture.dispose();
        }
        for (Texture texture : swordDown) {
            texture.dispose();
        }
        for (Texture texture : swordLeft) {
            texture.dispose();
        }
        for (Texture texture : swordRight) {
            texture.dispose();
        }
    }
}
