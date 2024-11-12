package com.Code.Screens;

import com.Code.Animation.Animation;
import com.Code.Animation.PlayerAnimation;
import com.Code.Box2D.Box2Dobject;
import com.Code.Entity.Player;
import com.Code.Main;
import com.Code.Map.Maploader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.*;

public class PlayScreen implements Screen {

    Main game;
    Animation animation;
    OrthographicCamera Camera;
    Viewport viewport;
    Sprite playerSprite;
    //map
    OrthogonalTiledMapRenderer mapRenderer;
    Maploader maploader;
    TiledMap map;

    World world;
    Box2Dobject box2Dobject;



    public PlayScreen(Main game){
        this.game = game;


        Camera = new OrthographicCamera();
        viewport = new FitViewport(game.ScreenWidth, game.ScreenHeight, Camera);
        //Map load
        maploader = new Maploader();
        map = maploader.CreateMap();
        mapRenderer = new OrthogonalTiledMapRenderer(map, game.batch);


        //Create world
        world = new World(new Vector2(0,0), true);
        box2Dobject = new Box2Dobject(map, world);
        box2Dobject.CreateCollision();
        box2Dobject.CreatePlayer();

        game.player = new Player(box2Dobject);
        animation = new Animation(game);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        mapRenderer.render();

        updateWorld();
        box2Dobject.box2DDebugRenderer.render(box2Dobject.world, Camera.combined);

        renderPlayer();
        renderCamera();
        game.batch.setProjectionMatrix(Camera.combined);

        game.batch.begin();
        playerSprite.draw(game.batch);
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

    }


    public void renderCamera(){
        Camera.zoom = 0.3f;
        Camera.position.set(game.player.playerPosition.x , game.player.playerPosition.y ,0);

        mapRenderer.setView(Camera);
        Camera.update();
    }

    public void renderPlayer(){

        playerSprite = new Sprite(animation.playerAnimation.Sprites);
        playerSprite.setPosition(game.player.playerPosition.x - game.BaseSize/2, game.player.playerPosition.y - game.BaseSize/2);
        playerSprite.setSize(game.BaseSize,game.BaseSize);
        //System.out.println(player.x + "   " + player.y);
    }

    public void updateWorld()
    {
        world.step(1/60f, 6, 2);
        game.player.update();
    }
}
