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

public class StoryScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Texture background, logo, backButtonTexture, nextButtonTexture;
    private Rectangle backRect, nextRect;
    private BitmapFont font;

    private String[] storyParts;  // Story split into parts
    private int currentPart;      // Current part index

    public StoryScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.getData().setScale(1.4f); // Adjust font size
        font.setColor(Color.GOLD); // Set font color

        // Initialize story parts
        this.storyParts = new String[] {
            "Adding to the alternate timeline:\n" +
                "Crimson Moon, a dormant ancient god, created a forest filled with blood and vitality, named after itself (Crimson Moon). " +
                "Within this forest, animals have been driven mad by the god's blood, making the area extremely dangerous but also the only place where organizations can study the gods. HUST refers to this area as X-37.\n" +
                "\n" +
                "After humanity's battle with a god, another Elder God – The Elder God of Death – secretly arrives on Earth-2407 with the intention of stealing Crimson Moon's power. " +
                "The Elder God seeks to create an army of Undead infused with the blood of the Crimson Moon, aiming to eradicate humanity and establish worlds ruled solely by gods.",

            "Moving to the main timeline:\nThe protagonist (player-defined name) – a scholar at the HUST organization and its finest warrior, under the guidance of Master William, " +
                "is chosen to research humans and gods, with the goal of merging humanity and divinity to advance human evolution.\n" +
                "HUST studies gods and humans with goals opposing those of Celestial Flame worshippers, who aim to eradicate the gods and push humanity into a godless world.\n" +
                "The protagonist will travel to the X-37 area to gather information about the Elder God of Blood.",

            "Key locations within X-37:\n" +
                "+) Hall Of Sins\n" +
                "+) Boss battle area (The Elder God of Death)\n" +
                "+) Albedo – The Lost City Of Dreams\n" +
                "+) The Graveyard of Mankind\n" +
                "The protagonist begins their journey at The Graveyard of Mankind, facing frenzied monsters and defeating the first undead encountered there.",

            "Encounter the first boss: The Prayer Of Sins. This entity, created by The Elder God of Death, is incredibly powerful. The protagonist cannot defeat it, " +
                "but if victorious, they will receive a reward and be teleported directly back to HUST's hospital.\n" +
                "Upon returning, the protagonist and Master William suspect the appearance of undead, a new discovery not documented in HUST's archives.",

            "HUST forms a special team, led by the protagonist, to delve deeper into X-37 and learn more about the Elder God of Blood.\n" +
                "At the Hall Of Sins, the protagonist discovers this area was HUST's research facility, where gruesome experiments were conducted to merge Crimson Moon's blood with humans.\n" +
                "The protagonist learns they are the only successful result of these experiments and a demi-god (the sole offspring of Crimson Moon).",

            "After returning to HUST, the protagonist encounters Kafka, a discarded experiment by the organization who later joined Celestial Flame.\n" +
                "Kafka reveals that HUST is, in fact, a malevolent organization that concealed the truth about its experiments while spreading propaganda against Celestial Flame.\n" +
                "The protagonist decides to investigate further, uncovering the secrets behind the undead created by The Elder God of Death and affirming HUST's cruelty."
        };
        this.currentPart = 0;  // Start with the first part
    }

    @Override
    public void show() {
        // Load textures
        background = new Texture(Gdx.files.internal("screens/background.png"));
        logo = new Texture(Gdx.files.internal("screens/logo.png"));
        backButtonTexture = new Texture(Gdx.files.internal("screens/back.png"));
        nextButtonTexture = new Texture(Gdx.files.internal("screens/next.png"));

        // Set up button positions
        int buttonWidth = 120;
        int buttonHeight = 60;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        backRect = new Rectangle(
            screenWidth / 2 - buttonWidth - 20, // Position left of center
            50,
            buttonWidth,
            buttonHeight
        );

        nextRect = new Rectangle(
            screenWidth / 2 + 20, // Position right of center
            50,
            buttonWidth,
            buttonHeight
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(logo, (Gdx.graphics.getWidth() - 400) / 2, Gdx.graphics.getHeight() - 150, 400, 100);

        // Render story text inside the spaceship window
        float textWidth = 800; // Fit within the spaceship window
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2; // Center horizontally
        float textY = Gdx.graphics.getHeight() / 2 + 150; // Adjust vertical position
        font.draw(batch, storyParts[currentPart], textX, textY, textWidth, -1, true);

        // Render buttons
        batch.draw(backButtonTexture, backRect.x, backRect.y, backRect.width, backRect.height);
        batch.draw(nextButtonTexture, nextRect.x, nextRect.y, nextRect.width, nextRect.height);
        batch.end();

        // Handle button input
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (backRect.contains(touchPos)) {
                game.setScreen(new MenuScreen(game)); // Return to the menu
            } else if (nextRect.contains(touchPos)) {
                currentPart++;
                if (currentPart >= storyParts.length) {
                    currentPart = storyParts.length - 1; // Stop at the last part
                }
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
        nextButtonTexture.dispose();
    }
}
