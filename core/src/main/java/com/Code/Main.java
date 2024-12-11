package com.Code;

import com.Code.Box2D.WorldContactListener;
import com.Code.Controller.KeyHandler;
import com.Code.Entity.ECSEngine;
import com.Code.Map.MapMangager;
import com.Code.Screens.PlayScreen;
import com.Code.Screens.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.assets.AssetManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;

    public static final float PPM = 1 / 16f;
    public static final float MAX_STEP_TIME = 1/60f;

    public float BaseSize = 16 * PPM;
    public int ScreenWidth = 800, ScreenHeight = 450;
    public SpriteBatch batch;
    public World world;

    public OrthographicCamera Camera;
    public KeyHandler keyHandler;
    public ECSEngine ecsEngine;
    public MapMangager mapMangager;
    public WorldContactListener worldContactListener;

    private AssetManager assetManager; // AssetManager để quản lý tài nguyên

    public static BodyDef bodyDef = new BodyDef();
    public static FixtureDef fixtureDef = new FixtureDef();

    @Override
    public void create() {
        batch = new SpriteBatch();

        assetManager = new AssetManager(); // Khởi tạo AssetManager

        keyHandler = new KeyHandler();
        Camera = new OrthographicCamera();
        world = new World(new Vector2(0, 0), true);

        ecsEngine = new ECSEngine(this);
        mapMangager = new MapMangager(this);
        mapMangager.setMap();
        worldContactListener = new WorldContactListener(this);
        world.setContactListener(worldContactListener);

        setScreen(new LoadingScreen(this)); // Chuyển tới màn hình loading khi game bắt đầu

        Gdx.input.setInputProcessor(keyHandler);
    }



    // Hàm để lấy AssetManager
    public AssetManager getAssetManager() {
        return assetManager;
    }


    public static void resetBox2D() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.isSensor = false;
        fixtureDef.shape = null;
    }
}
