package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MenuScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture background, logo, startButton, storyButton, guideButton, exitButton;
    private Rectangle startRect, storyRect, guideRect, exitRect; // Các vùng của nút

    public MenuScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        System.out.println("MenuScreen show() called");

        // Tải các hình ảnh
        background = new Texture(Gdx.files.internal("screens/background.png"));
        logo = new Texture(Gdx.files.internal("screens/logo.png"));
        startButton = new Texture(Gdx.files.internal("screens/start.png"));
        storyButton = new Texture(Gdx.files.internal("screens/story.png"));
        guideButton = new Texture(Gdx.files.internal("screens/guide.png"));
        exitButton = new Texture(Gdx.files.internal("screens/exit.png"));

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Logo rộng hơn về bề ngang: logoWidth = nửa chiều rộng màn hình
        // logoHeight nhỏ hơn logoWidth để logo trông ngang hơn (tỷ lệ khoảng 1:4.5)
        int logoWidth = screenWidth / 2;
        int logoHeight = screenHeight / 4;
        int logoX = (screenWidth - logoWidth) / 2;
        int logoY = screenHeight - logoHeight - screenHeight / 12;

        // Nút nhỏ hơn để nằm gọn trong khung cảnh
        // Giữ tỷ lệ 2:1 cho nút, chọn kích thước nhỏ hơn so với trước
        int buttonWidth = screenWidth / 10;
        int buttonHeight = buttonWidth / 2;
        int buttonSpacing = screenHeight / 30;

        // Đặt nút START bên dưới logo, cách logo một khoảng
        int startY = logoY - buttonHeight - 40;
        int buttonX = (screenWidth - buttonWidth) / 2;

        startRect = new Rectangle(buttonX, startY, buttonWidth, buttonHeight);
        storyRect = new Rectangle(buttonX, startY - (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        guideRect = new Rectangle(buttonX, startY - 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        exitRect = new Rectangle(buttonX, startY - 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Sử dụng lại các thông số logo
        int logoWidth = screenWidth / 2;
        int logoHeight = screenHeight / 4;
        int logoX = (screenWidth - logoWidth) / 2;
        int logoY = screenHeight - logoHeight - screenHeight / 12;

        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.draw(logo, logoX, logoY, logoWidth, logoHeight);

        // Vẽ nút với kích thước đã chỉnh
        batch.draw(startButton, startRect.x, startRect.y, startRect.width, startRect.height);
        batch.draw(storyButton, storyRect.x, storyRect.y, storyRect.width, storyRect.height);
        batch.draw(guideButton, guideRect.x, guideRect.y, guideRect.width, guideRect.height);
        batch.draw(exitButton, exitRect.x, exitRect.y, exitRect.width, exitRect.height);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (startRect.contains(touchPos)) {
                System.out.println("Start button clicked");
                game.setScreen(new PlayScreen(game));
            } else if (storyRect.contains(touchPos)) {
                System.out.println("Story button clicked");
                game.setScreen(new StoryScreen(game));
            } else if (guideRect.contains(touchPos)) {
                System.out.println("Guide button clicked");
                game.setScreen(new GuideScreen(game));
            } else if (exitRect.contains(touchPos)) {
                System.out.println("Exit button clicked");
                Gdx.app.exit();
            }
        }
    }


    @Override
    public void resize(int width, int height) {
        // Không cần thiết cho màn hình tĩnh
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        logo.dispose();
        startButton.dispose();
        storyButton.dispose();
        guideButton.dispose();
        exitButton.dispose();
    }
}
