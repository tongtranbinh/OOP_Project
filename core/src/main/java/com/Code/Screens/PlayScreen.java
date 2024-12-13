package com.Code.Screens;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.System.RenderingSystem;
import com.Code.Scenes.Hud;
import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Renderable;
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
    RenderingSystem renderingSystem;

    private Hud hud; // Thêm HUD

    public PlayScreen(Main game) {
        this.game = game;

        Camera = new OrthographicCamera();
        viewport = new FitViewport(game.ScreenWidth * Main.PPM, game.ScreenHeight * Main.PPM, Camera);

        // Map load
        mapRenderer = new OrthogonalTiledMapRenderer(game.mapMangager.currentMap.tiledMap, 1 * Main.PPM);

        // Tạo HUD
        hud = new Hud(game.ecsEngine);

        //render
        renderingSystem = new RenderingSystem(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.keyHandler);
    }

    @Override
    public void render(float delta) {
        // Kiểm tra nếu bấm phím P
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            game.setScreen(new PauseScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateWorld();

        renderCamera();
        mapRenderer.setView(Camera);
        mapRenderer.render();


        game.batch.setProjectionMatrix(Camera.combined);
        box2DDebugRenderer.render(game.world, Camera.combined);

        game.ecsEngine.update(1 / 60f);
        game.ecsEngine.destroyBody();

        renderingSystem.render(delta);
        // Vẽ HUD
        hud.render(game.batch, game.ScreenWidth, game.ScreenHeight);


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
        hud.dispose(); // Hủy HUD
        renderingSystem.dispose();
    }


    public void renderCamera(){
        Camera.zoom = 0.2f;
        Vector2 position = ECSEngine.box2DComponentMapper.get(game.mapMangager.ecsEngine.playerEntity).body.getPosition();
        Camera.position.set(position,0);

        mapRenderer.setView(Camera);
        Camera.update();
    }

    public void updateWorld() {
        game.world.step(1 / 60f, 6, 2);
    }
}
