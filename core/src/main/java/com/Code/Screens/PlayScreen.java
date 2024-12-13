package com.Code.Screens;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.BossComponent;
import com.Code.Scenes.Hud;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
        hud = new Hud(game.ecsEngine);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.keyHandler);
    }

    @Override
    public void render(float delta) {
        // Kiểm tra nếu boss chưa được thiết lập và đang trong map cuối
        if (bossEntity == null) {
            for (Entity entity : game.mapMangager.ecsEngine.getEntities()) {
                BossComponent boss = ECSEngine.bossComponentMapper.get(entity);
                if (boss != null) {
                    bossEntity = entity;
                    hud.setBossHealth(boss.maxLife); // Thiết lập thanh máu boss
                    break;
                }
            }
        }

        // Kiểm tra trạng thái và cập nhật thanh máu boss nếu boss tồn tại
        if (bossEntity != null) {
            BossComponent boss = ECSEngine.bossComponentMapper.get(bossEntity);
            if (boss != null) {
                if (boss.readytoAttack) {
                    hud.updateBossHealth(boss.currentLife); // Cập nhật máu boss khi đang tấn công
                } else {
                    hud.hideBossHealth(); // Ẩn thanh máu boss nếu không tấn công
                }
            }
        }

        // Dọn màn hình
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Cập nhật thế giới vật lý
        updateWorld();

        // Vẽ camera
        renderCamera();

        // Vẽ bản đồ
        mapRenderer.render();

        // Thiết lập batch và vẽ HUD
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

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
