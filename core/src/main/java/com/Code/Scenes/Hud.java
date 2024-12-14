package com.Code.Scenes;

import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Main;
import com.Code.Screens.EndScreen;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.Entity;


public class Hud {
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final ECSEngine ecsEngine;
    private final Main game; // Thêm Main game

    // Thêm thuộc tính để hiển thị thanh máu boss
    private float bossMaxHealth = 0;
    private float bossCurrentHealth = 0;
    private boolean bossHealthVisible = false;

    public Hud(ECSEngine ecsEngine, Main game) {
        this.ecsEngine = ecsEngine;
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
    }

    // Thêm phương thức để thiết lập thông tin về boss
    public void setBossHealth(float maxHealth) {
        this.bossMaxHealth = maxHealth;
        this.bossCurrentHealth = maxHealth;
        this.bossHealthVisible = true;
    }

    public void updateBossHealth(float currentHealth) {
        this.bossCurrentHealth = currentHealth;
    }

    public void hideBossHealth() {
        this.bossHealthVisible = false;
    }

    public void render(SpriteBatch spriteBatch, float screenWidth, float screenHeight) {
        Entity player = ecsEngine.playerEntity;
        if (player == null) return;

        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(player);
        if (playerComponent == null) return;

        float currentHealth = playerComponent.life;
        float maxHealth = playerComponent.maxLife;

        maxHealth = Math.max(maxHealth, 1);
        currentHealth = Math.max(currentHealth, 0);

        float barWidth = 220;
        float barHeight = 25;
        float barX = 590;
        float barY = 50;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        if (currentHealth > 0) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(barX, barY, (currentHealth / maxHealth) * barWidth, barHeight);
        }

        shapeRenderer.end();

        spriteBatch.begin();
        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "HP: " + (int) currentHealth + "/" + (int) maxHealth, barX + (barWidth / 4), barY + barHeight + 15);
        spriteBatch.end();

        if (currentHealth <= 0) {
            game.setScreen(new EndScreen(game));
            return;
        }

        // Vẽ thanh máu boss nếu bossHealthVisible = true
        if (bossHealthVisible) {
            float bossBarWidth = 300;
            float bossBarHeight = 20;
            float bossBarX = screenWidth / 2 - bossBarWidth / 2;
            float bossBarY = screenHeight - 50;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(bossBarX, bossBarY, bossBarWidth, bossBarHeight);

            if (bossCurrentHealth > 0) {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(bossBarX, bossBarY, (bossCurrentHealth / bossMaxHealth) * bossBarWidth, bossBarHeight);
            }
            shapeRenderer.end();
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }

}
