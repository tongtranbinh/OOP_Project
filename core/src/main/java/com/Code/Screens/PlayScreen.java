package com.Code.Screens;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.BossComponent;
import com.Code.Scenes.Hud;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.Entity;


public class PlayScreen implements Screen {

    Main game;
    OrthographicCamera Camera;
    Viewport viewport;
    OrthogonalTiledMapRenderer mapRenderer;

    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();

    private Hud hud;
    private Entity bossEntity;

    public PlayScreen(Main game) {
        this.game = game;

        Camera = new OrthographicCamera();
        viewport = new FitViewport(game.ScreenWidth * Main.PPM, game.ScreenHeight * Main.PPM, Camera);

        mapRenderer = new OrthogonalTiledMapRenderer(game.mapMangager.currentMap.tiledMap, 1 * Main.PPM);
        hud = new Hud(game.ecsEngine, game);

        // Lấy boss ngay từ đầu
        ImmutableArray<Entity> bosses = game.ecsEngine.getEntitiesFor(Family.all(BossComponent.class).get());
        if (bosses.size() > 0) {
            bossEntity = bosses.first();
            BossComponent boss = ECSEngine.bossComponentMapper.get(bossEntity);
            hud.setBossHealth(boss.maxLife); // Set thanh máu boss
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.keyHandler);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            game.setScreen(new PauseScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Cập nhật trạng thái boss
        if (bossEntity != null) {
            BossComponent boss = ECSEngine.bossComponentMapper.get(bossEntity);
            if (boss != null) {
                if (boss.readytoAttack && boss.currentLife > 0) {
                    hud.updateBossHealth(boss.currentLife); // Cập nhật thanh máu
                } else {
                    hud.hideBossHealth(); // Ẩn thanh máu nếu không cần
                }
            }
        }

        updateWorld();
        renderCamera();

        mapRenderer.render();

        game.batch.setProjectionMatrix(Camera.combined);
        box2DDebugRenderer.render(game.world, Camera.combined);

        game.ecsEngine.update(1 / 60f);
        game.ecsEngine.destroyBody();

        hud.render(game.batch, game.ScreenWidth, game.ScreenHeight);

        game.batch.begin();
        game.batch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }

    public void renderCamera() {
        Camera.zoom = 0.5f;
        Vector2 position = ECSEngine.box2DComponentMapper
            .get(game.mapMangager.ecsEngine.playerEntity)
            .body.getPosition();
        Camera.position.set(position, 0);

        mapRenderer.setView(Camera);
        Camera.update();
    }

    public void updateWorld() {
        game.world.step(1 / 60f, 6, 2);
    }
}
