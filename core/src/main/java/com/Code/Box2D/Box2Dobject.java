package com.Code.Box2D;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Box2Dobject {
    World world;
    Box2DDebugRenderer box2DDebugRenderer;

    public Box2Dobject(){
        world = new World(new Vector2(0,0),true);
        box2DDebugRenderer = new Box2DDebugRenderer();
    }
    public void CreatePLayer(){

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

    }


}
