package com.Code.Screens;

import com.Code.Animation.Animation;
import com.Code.Animation.PlayerAnimation;
import com.Code.Entity.Player;
import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    Player player;
    Main game;
    Animation animation;
    OrthographicCamera Camera;
    Viewport viewport;
    Sprite playerSprite;

    public PlayScreen(Main game){
        this.game = game;

        player = new Player(0,0);
        animation = new Animation();
        Camera = new OrthographicCamera();
        viewport = new FitViewport(game.ScreenWidth, game.ScreenHeight, Camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(Camera.combined);
        renderCamera();
        renderPlayer();
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
        Camera.zoom = 0.25f;
        Camera.position.set(player.x +game.BaseSize/2, player.y + game.BaseSize/2,0);
        Camera.update();
    }

    public void renderPlayer(){
        playerSprite = new Sprite(animation.playerAnimation.Sprites);
        playerSprite.setPosition(player.x, player.y);
        playerSprite.setSize(game.BaseSize,game.BaseSize);
    }
}
