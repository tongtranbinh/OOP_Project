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
    private Texture background, logo, resumeButton, exitButton;
    private Rectangle resumeRect, exitRect; // Các vùng của nút

    public PauseScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // Tải các hình ảnh giống giao diện MenuScreen
        background = new Texture(Gdx.files.internal("screens/background.png"));
        logo = new Texture(Gdx.files.internal("screens/logo.png"));
        resumeButton = new Texture(Gdx.files.internal("screens/continue.png"));
        exitButton = new Texture(Gdx.files.internal("screens/menu.png"));

        // Xác định vị trí và kích thước các thành phần
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Logo: giống MenuScreen
        int logoWidth = screenWidth / 2;
        int logoHeight = screenHeight / 4;
        int logoX = (screenWidth - logoWidth) / 2;
        int logoY = screenHeight - logoHeight - screenHeight / 12;

        // Nút Resume và Exit
        int buttonWidth = screenWidth / 10;
        int buttonHeight = buttonWidth / 2;
        int buttonSpacing = screenHeight / 30;

        int resumeY = logoY - buttonHeight - 40;
        int buttonX = (screenWidth - buttonWidth) / 2;

        resumeRect = new Rectangle(buttonX, resumeY, buttonWidth, buttonHeight);
        exitRect = new Rectangle(buttonX, resumeY - (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        // Xóa màn hình và hiển thị màn hình Pause
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Logo: giống MenuScreen
        int logoWidth = screenWidth / 2;
        int logoHeight = screenHeight / 4;
        int logoX = (screenWidth - logoWidth) / 2;
        int logoY = screenHeight - logoHeight - screenHeight / 12;

        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight); // Vẽ nền
        batch.draw(logo, logoX, logoY, logoWidth, logoHeight); // Vẽ logo
        batch.draw(resumeButton, resumeRect.x, resumeRect.y, resumeRect.width, resumeRect.height); // Vẽ nút Resume
        batch.draw(exitButton, exitRect.x, exitRect.y, exitRect.width, exitRect.height); // Vẽ nút Exit
        batch.end();

        // Xử lý click chuột
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (resumeRect.contains(touchPos)) {
                System.out.println("Resume button clicked");
                game.setScreen(new PlayScreen(game)); // Quay lại màn hình chơi
            } else if (exitRect.contains(touchPos)) {
                System.out.println("Exit button clicked");
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
        logo.dispose();
        resumeButton.dispose();
        exitButton.dispose();
    }
}
