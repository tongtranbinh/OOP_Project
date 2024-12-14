package com.Code.Entity.System;



import com.Code.Entity.Component.*;
import com.Code.Controller.KeyHandler;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.BossAnimation;
import com.Code.Others.DirectionType;
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
    int[] animationDownfixX = {15, 12, 0, 0, -3};
    int[] animationDownfixY = {12, 18, 15, 18, 15};
    int[] animationLeftfixX = {3, 9, 12, 9, 9};
    int[] animationLeftfixY = {6, 3, 0, 0, 0};
    int[] animationRightfixX = {-12, -18, -15, -15, -15};
    int[] animationRightfixY = {9, 9, 0, -3, 6};
    int[] animationUpfixX = {15, 15, 12, 9, 6};
    int[] animationUpfixY = {9, 15, 12, 15, 12};
    KeyHandler keyHandler;



    public Texture currentTexture = null;
    public ArrayList<Texture> up = new ArrayList<Texture>();
    public ArrayList<Texture> down = new ArrayList<Texture>();
    public ArrayList<Texture> right = new ArrayList<Texture>();
    public ArrayList<Texture> left = new ArrayList<Texture>();
    private float stateTime = 0f; // Thời gian trạng thái để điều chỉnh animation
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
    private boolean isAttacking = false;
    private boolean isAttacking1 = false;
    private boolean isAttacking2 = false;




    public RenderingSystem(Main game) {
        this.game = game;
        this.batch = game.batch;
        bossAnimation = new BossAnimation();
        keyHandler = game.keyHandler;
        CreatePlayerAnimation();
    }

    public void render(float delta){
        Statetime += delta;
        batch.begin();
        renderBoss(delta);

        renderDamagedArea(delta);
        renderEnemy(delta);

        handlePlayerAttack(delta);
        if(!isAttacking) {
            renderPLayer(delta);
        }
        if(isAttacking){
            if(isAttacking1) renderFightAnimation(delta);
            else renderHandAnimation(delta);
        }
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
        Box2DComponent box2DComponent = entity.getComponent(Box2DComponent.class);
        Vector2 position = box2DComponent.body.getPosition();
        if (!keyHandler.up &&
            !keyHandler.down &&
            !keyHandler.left &&
            !keyHandler.right) {
            // Cập nhật thời gian để điều chỉnh frame của animation
            stateTime += delta;  // Cộng deltaTime vào stateTime để tạo chuyển động mượt mà

            // Tính toán chỉ số frame hiện tại
            int frameIndex = (int) (stateTime * 10) % 12;  // Mỗi sprite hiện trong 0.1 giây, có 12 frame

            // Chọn danh sách sprites dựa trên hướng di chuyển của Player
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

            // Vẽ sprite lên màn hình tại vị trí của Player
            batch.draw(currentTexture, position.x - game.BaseSize * 1.5f, position.y - game.BaseSize * 1.5f, game.BaseSize * 3, game.BaseSize * 3);
        }else { // Cập nhật thời gian để điều chỉnh frame của animation
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
            batch.draw(currentTexture, position.x - 1.5f * game.BaseSize, position.y - 1.5f * game.BaseSize, 3f, 3f);
        }

    }
    public void handlePlayerAttack(float delta) {

        if ((keyHandler.isAttack1 || keyHandler.isAttack2)  && !isAttacking) {
            if(keyHandler.isAttack1) isAttacking1 = true;
            else isAttacking2 = true;
            isAttacking = true;
            stateTime = 0f; // Reset stateTime khi bắt đầu đánh
        }


    }

    public void renderFightAnimation(float delta) {

        if (!isAttacking) {
            return;
        }
        // Cập nhật thời gian để điều chỉnh frame của animation
        stateTime += delta;  // Cộng deltaTime vào stateTime để tạo chuyển động mượt mà

        // Tính toán chỉ số frame hiện tại
        int frameIndex = (int) (stateTime / 0.15f) % 5; // Mỗi frame hiển thị trong 0.1 giây

        Entity entity = game.ecsEngine.playerEntity;
            //Player Position Info
            Box2DComponent box2DComponent = entity.getComponent(Box2DComponent.class);
            Vector2 position = box2DComponent.body.getPosition();
            // Lấy frame hiện tại dựa trên hướng và loại hoạt ảnh
            PlayerComponent playerComponent = game.ecsEngine.playerComponentMapper.get(entity);



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
                    batch.draw(
                        currentTexture,
                        position.x - 1.5f * game.BaseSize,
                        position.y - 1.0f * game.BaseSize,
                        3f, 3f
                    );
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
            System.out.println(frameIndex);

            batch.draw(currentTexture, position.x - 1.5f * game.BaseSize, position.y - 1.5f * game.BaseSize, 3f, 3f);
            if (stateTime >= 0.6f) { // Giả sử hoạt ảnh đánh kéo dài 0.5 giây
                isAttacking = false;
                isAttacking1 = false;
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
                            batch.draw(
                                currentTexture,
                                position.x - 1.5f * game.BaseSize - animationDownfixX[frameIndex] * Main.PPM, // lệch trái
                                position.y - 1.5f * game.BaseSize - animationDownfixY[frameIndex] * Main.PPM, // Vẽ thấp hơn một chút
                                3f, 3f
                            );
                            break;
                        case LEFT:

                                batch.draw(currentTexture,
                                    position.x - 1.5f * game.BaseSize - animationLeftfixX[frameIndex] * Main.PPM, // Vẽ lệch trái một chút
                                    position.y - 1.5f * game.BaseSize - animationLeftfixY[frameIndex] * Main.PPM, // Giữ nguyên vị trí Y
                                    3f, 3f);

                            break;
                        case RIGHT:
                            batch.draw(
                                currentTexture,
                                position.x - 1.5f * game.BaseSize - animationRightfixX[frameIndex] * Main.PPM, // Vẽ lệch phải một chút
                                position.y - 1.5f * game.BaseSize - animationRightfixY[frameIndex] * Main.PPM, // Giữ nguyên vị trí Y
                                3f, 3f
                            );
                            break;
                    }
                }

        }


        batch.draw(currentTexture, position.x - game.BaseSize * 1.5f , position.y - game.BaseSize * 1.5f, game.BaseSize * 3 , game.BaseSize * 3);
    }
    public void renderHandAnimation(float delta) {

        if (!isAttacking) {
            return;
        }
        // Cập nhật thời gian để điều chỉnh frame của animation
        stateTime += delta;

        // Tính toán chỉ số frame hiện tại
        int frameIndex = (int) (stateTime / 0.15f) % 5; // Mỗi frame hiển thị trong 0.1 giây
        Entity entity = game.ecsEngine.playerEntity;
        //Player Position Info
        Box2DComponent box2DComponent = entity.getComponent(Box2DComponent.class);
        Vector2 position = box2DComponent.body.getPosition();
        // Lấy frame hiện tại dựa trên hướng và loại hoạt ảnh
        PlayerComponent playerComponent = game.ecsEngine.playerComponentMapper.get(entity);


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
        if (stateTime >= 0.6f) { // Giả sử hoạt ảnh đánh kéo dài 0.5 giây
            isAttacking = false;
            isAttacking2 = false;
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

