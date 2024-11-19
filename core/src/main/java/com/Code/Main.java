package com.Code;

import com.Code.Box2D.Box2Dobject;
import com.Code.Entity.Player;
import com.Code.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;


import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public int BaseSize = 32;
    public int ScreenWidth = 1600,ScreenHeight = 900;
    public SpriteBatch batch;
    public Player player;
    public Box2Dobject box2Dobject;
    public World world;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

}
