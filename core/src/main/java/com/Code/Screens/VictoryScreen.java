package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class VictoryScreen implements Screen {

    private Main game;
    private SpriteBatch batch;

    private Texture background, logo, menuButton;
    private Rectangle menuRect;

    public VictoryScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // Load textures
        background = new Texture(Gdx.files.internal("assets/screens/background.png"));
        logo = new Texture(Gdx.files.internal("assets/screens/victory.png")); // Sử dụng logo victory
        menuButton = new Texture(Gdx.files.internal("assets/screens/menu.png"));

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Thiết lập vùng nút menu (nhỏ như trong EndScreen)
        int menuWidth = 140;
        int menuHeight = 70;
        int menuX = screenWidth - menuWidth - 20;
        int menuY = 20;

        menuRect = new Rectangle(menuX, menuY, menuWidth, menuHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        // Thiết lập kích thước và vị trí logo victory
        int logoWidth = 600;
        int logoHeight = 400;
        int logoX = (screenWidth - logoWidth) / 2;
        int logoY = (screenHeight - logoHeight) / 2;

        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.draw(logo, logoX, logoY, logoWidth, logoHeight);
        batch.draw(menuButton, menuRect.x, menuRect.y, menuRect.width, menuRect.height);
        batch.end();

        // Xử lý sự kiện chạm
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (menuRect.contains(touchPos)) {
                System.out.println("Menu button clicked");
                game.setScreen(new MenuScreen(game));
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
        batch.dispose();
        background.dispose();
        logo.dispose();
        menuButton.dispose();
    }
}
