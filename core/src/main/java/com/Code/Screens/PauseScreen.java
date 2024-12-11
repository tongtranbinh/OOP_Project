package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PauseScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture background, resumeButton, menuButton;
    private Rectangle resumeRect, menuRect; // Các vùng của nút

    public PauseScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // Tải các hình ảnh
        background = new Texture(Gdx.files.internal("screens/background.png")); // Nền màn hình Pause
        resumeButton = new Texture(Gdx.files.internal("screens/start.png")); // Nút Resume
        menuButton = new Texture(Gdx.files.internal("screens/exit.png")); // Nút Menu

        // Xác định vị trí và kích thước các nút
        int buttonWidth = 200;
        int buttonHeight = 50;
        int centerX = 1600 / 2; // Trung tâm màn hình theo chiều ngang
        int centerY = 900 / 2; // Trung tâm màn hình theo chiều dọc

        // Vị trí các nút
        resumeRect = new Rectangle(centerX - buttonWidth / 2, centerY + 30, buttonWidth, buttonHeight);
        menuRect = new Rectangle(centerX - buttonWidth / 2, centerY - 70, buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        // Xóa màn hình và hiển thị màn hình Pause
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1600, 900); // Vẽ nền
        batch.draw(resumeButton, resumeRect.x, resumeRect.y, resumeRect.width, resumeRect.height); // Vẽ nút Resume
        batch.draw(menuButton, menuRect.x, menuRect.y, menuRect.width, menuRect.height); // Vẽ nút Menu
        batch.end();

        // Xử lý click chuột
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (resumeRect.contains(touchPos)) {
                System.out.println("Resume button clicked");
                game.setScreen(new PlayScreen(game)); // Quay lại màn hình chơi
            } else if (menuRect.contains(touchPos)) {
                System.out.println("Menu button clicked");
                game.setScreen(new MenuScreen(game)); // Quay lại màn hình menu
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Giải phóng tài nguyên
        batch.dispose();
        background.dispose();
        resumeButton.dispose();
        menuButton.dispose();
    }
}
