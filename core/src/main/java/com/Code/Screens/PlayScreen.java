package com.Code.Screens;

import com.Code.Entity.ECSEngine;
import com.Code.Entity.System.PhysicDebugSystem;
import com.Code.Main;
import com.Code.Map.MapMangager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.Code.Main.MAX_STEP_TIME;

public class PlayScreen implements Screen {


    Main game;
    OrthographicCamera Camera;
    Viewport viewport;
    //map
    OrthogonalTiledMapRenderer mapRenderer;
    MapMangager mapMangager;

    float accumulator = 0;


    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();


    public PlayScreen(Main game){
        this.game = game;

        //Create world


        this.Camera = game.Camera;
        viewport = new FitViewport(game.ScreenWidth * Main.PPM, game.ScreenHeight * Main.PPM, Camera);
        //Map load
        mapMangager = game.mapMangager;
        mapRenderer = new OrthogonalTiledMapRenderer(mapMangager.currentMap.tiledMap, 1 * Main.PPM);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        updateWorld(delta);
        game.ecsEngine.update(delta);



        renderCamera();

        mapRenderer.setView(Camera);
        mapRenderer.render();
        game.batch.setProjectionMatrix(Camera.combined);

        box2DDebugRenderer.render(game.world,Camera.combined);
        game.ecsEngine.destroyBody();
        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
    }

    public void updateWorld(float delta){

        accumulator += delta;

        // Đảm bảo bước thời gian cố định
        while (accumulator >= MAX_STEP_TIME) {
            game.world.step(MAX_STEP_TIME, 8, 2);
            accumulator -= MAX_STEP_TIME;
            if (accumulator > 5 * MAX_STEP_TIME) { // Ngăn quá tải
                accumulator = 0;
                break;
            }
        }


    }

    public void renderCamera(){
        Vector2 position = ECSEngine.box2DComponentMapper.get(mapMangager.ecsEngine.playerEntity).body.getPosition();
        Camera.position.set(position,0);

        mapRenderer.setView(Camera);
        Camera.update();
    }


}
