package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EndScreen implements Screen {

    private Main game;
    private SpriteBatch batch;

    private Texture defeatImage; // Hình ảnh thất bại
    private Texture restartButton; // Nút Restart
    private Texture exitButton; // Nút Exit

    private Rectangle restartRect; // Vùng của nút Restart
    private Rectangle exitRect; // Vùng của nút Exit

    public EndScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();

        // Load hình ảnh
        defeatImage = new Texture("assets/screens/guide.png");
        restartButton = new Texture("assets/screens/start.png");
        exitButton = new Texture("assets/screens/exit.png");

        // Kích thước và vị trí các nút
        float buttonWidth = 200;
        float buttonHeight = 80;

        float centerX = (game.ScreenWidth - buttonWidth) / 2f;
        float restartY = 300; // Y vị trí nút Restart
        float exitY = 200; // Y vị trí nút Exit

        restartRect = new Rectangle(centerX, restartY, buttonWidth, buttonHeight);
        exitRect = new Rectangle(centerX, exitY, buttonWidth, buttonHeight);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Hiển thị hình ảnh "Defeat"
        float imageWidth = 400;
        float imageHeight = 200;
        float imageX = (game.ScreenWidth - imageWidth) / 2f;
        float imageY = 500; // Cách cạnh trên
        batch.draw(defeatImage, imageX, imageY, imageWidth, imageHeight);

        // Vẽ các nút
        batch.draw(restartButton, restartRect.x, restartRect.y, restartRect.width, restartRect.height);
        batch.draw(exitButton, exitRect.x, exitRect.y, exitRect.width, exitRect.height);

        batch.end();

        // Xử lý sự kiện click chuột
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (restartRect.contains(touchPos)) {
                System.out.println("Restart button clicked");
                game.setScreen(new PlayScreen(game)); // Chơi lại game
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
        batch.dispose();
        defeatImage.dispose();
        restartButton.dispose();
        exitButton.dispose();
    }
}
