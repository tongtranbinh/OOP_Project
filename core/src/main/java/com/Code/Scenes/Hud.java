package com.Code.Scenes;

import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.PlayerComponent;
import com.Code.Entity.System.RenderingSystem;
import com.Code.Main;
import com.Code.Screens.EndScreen;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;


public class Hud {
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final ECSEngine ecsEngine;
    private final Main game; // Thêm Main game

    // Thêm thuộc tính để hiển thị thanh máu boss
    private float bossMaxHealth = 0;
    private float bossCurrentHealth = 0;
    private boolean bossHealthVisible = false;
    private Sprite playerBar, playerHealth, playerImage, bossBar, bossHealth;
    private Texture texture, textureboss;
    static float trans = 1/100f;
    RenderingSystem renderingSystem;

    public Hud(ECSEngine ecsEngine, Main game) {
        this.ecsEngine = ecsEngine;
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.renderingSystem = game.renderingSystem;
        playerBar = new Sprite(new Texture("Boss/bar.png"));
        bossBar = new Sprite(new Texture("Boss/BossBar.png"));
        playerImage = new Sprite(new Texture("Player/Walk/Walk Down-split/imageonline/00.png"));
        texture = new Texture("Boss/health.png");
        textureboss = new Texture("Boss/BossHealth.png");
    }

    // Thêm phương thức để thiết lập thông tin về boss
    public void setBossHealth(float maxHealth) {
        this.bossMaxHealth = maxHealth;
        this.bossHealthVisible = true;
    }


    public void render(float screenWidth, float screenHeight) {
        Entity player = ecsEngine.playerEntity;
        if (player == null) return;

        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(player);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(player);
        if (playerComponent == null) return;

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font/NormalFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        float currentHealth = playerComponent.life;
        float maxHealth = playerComponent.maxLife;


        maxHealth = Math.max(maxHealth, 1);
        currentHealth = Math.max(currentHealth, 0);




        float barWidth = 1420 * trans;
        float barHeight = 432  * trans;
        Vector2 pos = box2DComponent.body.getPosition();

        float barX = pos.x - 28;
        float barY = pos.y + 11.5f;
        int scale = (int) (930 * (currentHealth / maxHealth));
        TextureRegion textureRegion = new TextureRegion(texture, 0 , 0, scale, 150);
        playerHealth = new Sprite(textureRegion);

        game.batch.begin();
        playerBar.setBounds(barX, barY, barWidth, barHeight);
        playerBar.draw(game.batch);

        playerImage.setBounds(barX + 1, barY + 1, 242 * trans, 242 * trans);
        playerImage.draw(game.batch);

        playerHealth.setBounds(barX + 430 * trans, barY + 35 * trans ,scale * trans, 150 * trans);
        playerHealth.draw(game.batch);
        game.batch.end();

        if (currentHealth <= 0) {
            game.setScreen(new EndScreen(game));
            return;
        }

        ImmutableArray<Entity> bosses = game.ecsEngine.getEntitiesFor(Family.all(BossComponent.class).get());
        for(Entity bossEntity : bosses) {
            BossComponent boss = ECSEngine.bossComponentMapper.get(bossEntity);
            if(boss.readytoAttack){
                setBossHealth(boss.maxLife); // Set thanh máu boss
                 bossCurrentHealth = boss.life;
            }
        }

        // Vẽ thanh máu boss nếu bossHealthVisible = true
        if(bossCurrentHealth <= 0) return;
        if (bossHealthVisible) {

            int bossX = (int) (882 * (bossCurrentHealth / bossMaxHealth));
            TextureRegion tt = new TextureRegion(textureboss, 0 , 0, bossX, 81);
            bossHealth = new Sprite(tt);

            game.batch.begin();
            bossBar.setBounds(pos.x - 15, pos.y - 15, 3000 * trans,125 * trans);
            bossBar.draw(game.batch);

            bossHealth.setBounds(pos.x - 14.5f, pos.y + -14.75f,bossX * trans * 3000/ 882, 81 * trans);
            bossHealth.draw(game.batch);
            game.batch.end();


        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }

}
