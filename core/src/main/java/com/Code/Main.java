package com.Code;

import com.Code.Entity.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    final int BaseTileSize = 32;
    final int Scale =  3;
    public final float BaseSize = BaseTileSize * Scale;
    final int ScreenCol = 16;
    final int ScreenRow = 9;
    public final float ScreenWidth = ScreenCol * BaseSize  ;
    public final float ScreenHeight = ScreenRow * BaseSize ;
    SpriteBatch batch;
    Player player = new Player();
    OrthographicCamera Camera = new OrthographicCamera();
    public float ViewportWidth = ScreenWidth;
    public float ViewportHeight = ScreenHeight;


    public void create() {
        batch = new SpriteBatch();
        player.setDefaultValue();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Camera = new OrthographicCamera(ViewportWidth, ViewportHeight * height/width);
        Camera.position.set(player.x,player.y,0);
        Camera.update();
    }


    @Override
    public void resize(int width, int height) {
        Camera.viewportWidth = width;
        Camera.viewportHeight = height;
        Camera.update();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        renderCamera();
        player.update();
        batch.begin();
        renderPlayer();
        batch.end();
    }

    public void renderCamera()
    {
        batch.setProjectionMatrix(Camera.combined);
        Camera.position.set(player.x + BaseSize/2, player.y + BaseSize/2,0);
        Camera.update();
    }
    public void renderPlayer(){
        Sprite playerSprite = new Sprite(player.Image);
        playerSprite.setPosition(player.x, player.y);
        playerSprite.setSize(BaseSize, BaseSize);
        playerSprite.draw(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
