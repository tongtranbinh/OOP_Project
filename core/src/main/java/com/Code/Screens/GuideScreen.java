package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GuideScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture background, logo, backButtonTexture;
    private Rectangle backRect;
    private BitmapFont font;

    private String guideText = "Welcome to the Guide!\n\n" +
        "1. **Movement**: Use WASD to move your character smoothly across the map.\n" +
        "2. **Shoot**: Press 'J' to fire your weapon at enemies.\n" +
        "3. **Pause**: Press 'P' to pause the game and access options.\n" +
        "4. **Goal**: Defeat enemies, level up, and face the ultimate boss to claim victory!\n\n" +
        "Tips:\n" +
        "- Explore every corner to find hidden treasures.\n" +
        "- Upgrade your abilities by defeating monsters.\n" +
        "- Plan your strategy to take down the final boss.";

    public GuideScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.getData().setScale(1.4f); // Giảm kích cỡ font một chút
        font.setColor(Color.GOLD); // Màu chữ vàng
    }

    @Override
    public void show() {
        // Load textures
        background = new Texture(Gdx.files.internal("screens/background.png"));
        logo = new Texture(Gdx.files.internal("screens/logo.png"));
        backButtonTexture = new Texture(Gdx.files.internal("screens/back.png"));

        // Set positions for the back button
        int buttonWidth = 100; // Giảm kích cỡ nút
        int buttonHeight = 50;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        backRect = new Rectangle(
            (screenWidth - buttonWidth) / 2,
            50, // Position near the bottom of the screen
            buttonWidth,
            buttonHeight
        );
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background and logo
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(logo, (Gdx.graphics.getWidth() - 400) / 2, Gdx.graphics.getHeight() - 150, 400, 100);

        // Draw the guide text, shifted slightly to the right
        float textWidth = 1000; // Adjust width for the text area
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2 + 120; // Dịch sang phải
        float textY = Gdx.graphics.getHeight() / 2 + 150; // Adjust vertical position
        font.draw(batch, guideText, textX, textY, textWidth, -1, true);

        // Draw the back button
        batch.draw(backButtonTexture, backRect.x, backRect.y, backRect.width, backRect.height);
        batch.end();

        // Handle input for the back button
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (backRect.contains(touchPos)) {
                game.setScreen(new MenuScreen(game)); // Return to the menu screen
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
        font.dispose();
        background.dispose();
        logo.dispose();
        backButtonTexture.dispose();
    }
}
