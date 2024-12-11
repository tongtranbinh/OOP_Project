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

        // Kích thước và khoảng cách
        int buttonWidth = 150; // Chiều rộng nút
        int buttonHeight = 50; // Chiều cao nút
        int buttonSpacing = 20; // Khoảng cách giữa các nút

        int screenWidth = 1600; // Chiều rộng màn hình
        int screenHeight = 900; // Chiều cao màn hình

        // Vị trí logo
        int logoWidth = 250; // Kích thước logo
        int logoHeight = 250;
        int logoX = (screenWidth - logoWidth) / 2; // Căn giữa theo trục ngang
        int logoY = screenHeight - logoHeight - 100; // Logo cách đỉnh màn hình 100px

        // Vị trí các nút
        int startY = logoY - 50 - buttonHeight; // Nút START nằm dưới logo 50px
        int buttonX = (screenWidth - buttonWidth) / 2; // Căn giữa theo trục ngang
        startRect = new Rectangle(buttonX, startY, buttonWidth, buttonHeight);
        storyRect = new Rectangle(buttonX, startY - (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        guideRect = new Rectangle(buttonX, startY - 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        exitRect = new Rectangle(buttonX, startY - 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        // Xóa màn hình và bật blending
        Gdx.gl.glClearColor(0, 0, 0, 1); // Màu nền đen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND); // Bật blending

        batch.begin();
        batch.draw(background, 0, 0, 1600, 900); // Vẽ hình nền

        // Vẽ logo
        int logoWidth = 250;
        int logoHeight = 250;
        int logoX = (1600 - logoWidth) / 2;
        int logoY = 900 - logoHeight - 100;
        batch.draw(logo, logoX, logoY, logoWidth, logoHeight);

        // Vẽ các nút
        batch.draw(startButton, startRect.x, startRect.y, startRect.width, startRect.height);
        batch.draw(storyButton, storyRect.x, storyRect.y, storyRect.width, storyRect.height);
        batch.draw(guideButton, guideRect.x, guideRect.y, guideRect.width, guideRect.height);
        batch.draw(exitButton, exitRect.x, exitRect.y, exitRect.width, exitRect.height);
        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND); // Tắt blending

        // Xử lý sự kiện click chuột
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
        // Giải phóng tài nguyên
        batch.dispose();
        background.dispose();
        logo.dispose();
        startButton.dispose();
        storyButton.dispose();
        guideButton.dispose();
        exitButton.dispose();
    }
}
