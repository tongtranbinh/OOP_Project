package com.Code.Scenes;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Screens.EndScreen;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Hud {
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final ECSEngine ecsEngine;

    public Hud(ECSEngine ecsEngine) {
        this.ecsEngine = ecsEngine;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
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

        if (currentHealth <= 0 && !(ecsEngine.getGame().getScreen() instanceof EndScreen)) {
            ecsEngine.getGame().setScreen(new EndScreen(ecsEngine.getGame()));
        }
    }


    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
