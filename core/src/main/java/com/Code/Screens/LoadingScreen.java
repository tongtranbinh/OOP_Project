package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen {

    Main parent;
    private SpriteBatch sb;
    private TextureAtlas atlas;
    private TextureRegion title, dash;
    private float stateTime;

    public LoadingScreen(Main parent) {
        this.parent = parent;
        sb = new SpriteBatch();
    }

    @Override
    public void show() {
        AssetManager assetManager = parent.getAssetManager();
        assetManager.load("images/loading.atlas", TextureAtlas.class);
        assetManager.load("images/game.atlas", TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear màn hình

        // Vẽ logo khi tải dữ liệu
        sb.begin();
        sb.draw(title, 135, 250);
        sb.end();

        // Kiểm tra xem AssetManager đã tải xong hay chưa
        AssetManager assetManager = parent.getAssetManager();
        if (assetManager.update()) {
            // Tải xong, chuyển sang màn hình chính
            parent.setScreen(new PlayScreen(parent));
        } else {
            // Nếu chưa tải xong, hiển thị tiến trình
            float progress = assetManager.getProgress();
            sb.begin();
            sb.draw(dash, 50, 150, 50 * progress, 50);  // Vẽ thanh tiến trình
            sb.end();
        }
    }


    @Override
    public void resize(int width, int height) {
        // Thay đổi kích thước màn hình nếu cần
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        sb.dispose();
    }
}
