    package com.Code.Screens;

    import com.Code.Main;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;

    public class PauseScreen implements Screen {

        private Main game;
        private SpriteBatch batch;
        private BitmapFont font;

        public PauseScreen(Main game) {
            this.game = game;
            batch = new SpriteBatch();
            font = new BitmapFont();
        }

        @Override
        public void show() {
            Gdx.input.setInputProcessor(new com.badlogic.gdx.InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
                        game.setScreen(new PlayScreen(game)); // Quay lại màn hình chơi khi nhấn Enter
                        return true;
                    }
                    return false;
                }
            });
        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            font.draw(batch, "Game Paused", 100, 100);
            font.draw(batch, "Press ENTER to Resume", 100, 150);
            batch.end();

            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                game.setScreen(new PlayScreen(game)); // Quay lại màn hình chơi khi nhấn Enter
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
        }
    }
