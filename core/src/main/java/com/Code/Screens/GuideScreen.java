package com.Code.Screens;

import com.Code.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GuideScreen implements Screen {

    private Main game;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private SpriteBatch batch;
    private TextButton backButton;

    private String guideText = "Welcome to the Guide!\n\n" +
        "1. **Movement**: Use arrow keys or WASD to move.\n" +
        "2. **Attack**: Press 'Space' to attack.\n" +
        "3. **Interact**: Press 'E' to interact with objects.\n" +
        "4. **Inventory**: Press 'I' to open the inventory.\n\n" +
        "Tips:\n" +
        "- Always check your surroundings for hidden items.\n" +
        "- Defeat enemies to level up and gain abilities.\n" +
        "- Explore new areas to find powerful weapons.\n";

    public GuideScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
    }

    @Override
    public void show() {
        // Thiết lập stage và skin cho nút
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Cung cấp skin cho nút

        // Tạo nút Back
        backButton = new TextButton("Back", skin);
        backButton.setPosition(300, 50); // Nút quay lại ở phía dưới
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Quay lại màn hình Menu khi nhấn Back
            }
        });

        // Thêm nút vào stage
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  // Xóa màn hình

        batch.begin();
        font.draw(batch, guideText, 50, 400);  // Hiển thị hướng dẫn ở vị trí (50, 400)
        batch.end();

        // Vẽ Stage (nút Back)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);  // Cập nhật viewport khi thay đổi kích thước
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
        font.dispose();
        stage.dispose();
    }
}
