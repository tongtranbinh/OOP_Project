package com.Code.Screens;

import com.Code.Animation.Animation;
import com.Code.Animation.PlayerAnimation;
import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class PlayScreen implements Screen {
    Main game;
    Animation animation;
    Texture image = new Texture("libgdx.png");
    PlayerAnimation playerAnimation = new PlayerAnimation();

    public PlayScreen(Main game){
        animation = new Animation();
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(animation.playerAnimation.Sprites,0,0);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
}
