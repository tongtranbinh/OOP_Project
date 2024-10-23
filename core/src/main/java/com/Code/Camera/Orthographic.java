package com.Code.Camera;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Orthographic implements ApplicationListener {

    public OrthographicCamera camera;
    public float ViewportWidth = 100f;
    public float ViewportHeight = 100f;



    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = ViewportWidth;
        camera.viewportHeight = ViewportHeight * height/width;
        camera.update();
    }

    @Override
    public void render() {;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
