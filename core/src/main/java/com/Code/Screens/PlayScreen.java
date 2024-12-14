package com.Code.Screens;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.System.RenderingSystem;
import com.Code.Scenes.Hud;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class PlayScreen implements Screen {

    Main game;
    OrthographicCamera Camera;
    Viewport viewport;
    OrthogonalTiledMapRenderer mapRenderer;

    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    RenderingSystem renderingSystem;

    public Hud hud;
    private Entity bossEntity;
    private Music bgm; // Thêm biến nhạc

    public PlayScreen(Main game) {
        this.game = game;

        this.renderingSystem = game.renderingSystem;
        Camera = new OrthographicCamera();
        viewport = new FitViewport(game.ScreenWidth * Main.PPM, game.ScreenHeight * Main.PPM, Camera);

        mapRenderer = new OrthogonalTiledMapRenderer(game.mapMangager.currentMap.tiledMap, 1 * Main.PPM);
        game.hud = hud;

        // Load nhạc
        bgm = Gdx.audio.newMusic(Gdx.files.internal("assets/music/sound.mp3"));
        bgm.setLooping(true); // Nhạc sẽ tự động lặp khi hết

        renderingSystem = new RenderingSystem(game);



    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.keyHandler);
        bgm.play(); // Bắt đầu phát nhạc khi vào màn chơi
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            game.setScreen(new PauseScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        updateWorld();
        renderCamera();

        mapRenderer.render();

        game.batch.setProjectionMatrix(Camera.combined);
        box2DDebugRenderer.render(game.world, Camera.combined);

        game.ecsEngine.update(1 / 60f);
        game.ecsEngine.destroyBody();

        renderingSystem.render(1/60f);
        hud.render( game.ScreenWidth, game.ScreenHeight);

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
    public void hide() {
        // Không dừng nhạc ở đây,
        // nếu bạn muốn nhạc dừng khi chuyển ra menu khác, dừng tại chỗ setScreen sang MenuScreen
    }

    @Override
    public void dispose() {
        // Dừng và giải phóng nhạc khi màn chơi dispose
        bgm.stop();
        bgm.dispose();

        game.world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }

    public void renderCamera() {
        Camera.zoom = 0.6f;
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
