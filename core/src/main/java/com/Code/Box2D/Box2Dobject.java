package com.Code.Box2D;

import com.Code.Entity.Player;
import com.Code.Map.Maploader;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Box2Dobject extends Sprite {
    public World world;
    public Box2DDebugRenderer box2DDebugRenderer;
    TiledMap map;

    public Box2Dobject(TiledMap map, World world){
        box2DDebugRenderer = new Box2DDebugRenderer();
        this.map = map;
        this.world = world;
    }
    public void CreateCollision(){

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        MapLayer collisionsLayer = map.getLayers().get("Collision");
        MapObjects collisions = collisionsLayer.getObjects();
        for(MapObject collision : collisions){
            if(collision instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) collision).getRectangle();

                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

                body = world.createBody(bodyDef);

                shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                fixtureDef.shape = shape;

                body.createFixture(fixtureDef);
            }
            if(collision instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) collision).getPolygon();

                //lay dinh tu polygon
                float verticles[] = polygon.getTransformedVertices();

                ChainShape cShape = new ChainShape();
                cShape.createLoop(verticles);

                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(polygon.getOriginX(), polygon.getOriginY());
                body = world.createBody(bodyDef);

                fixtureDef.shape = cShape;
                body.createFixture(fixtureDef);

                cShape.dispose();
                //System.out.println(polygon.getOriginX() + "   " + polygon.getOriginY());
            }

        }
    }

    public Body playerBody;
    public float playerX,playerY;
    public void CreatePlayer(){

        BodyDef bodyDef = new BodyDef();
        CircleShape shape = new CircleShape();
        FixtureDef fixtureDef = new FixtureDef();
        MapLayer pointLayer = map.getLayers().get("Player");
        MapObjects box2Dplayers = pointLayer.getObjects();

        for(MapObject box2Dplayer : box2Dplayers){

            if(box2Dplayer instanceof RectangleMapObject){
                RectangleMapObject obj = ((RectangleMapObject) box2Dplayer);
                Rectangle rect = obj.getRectangle();
                playerX = rect.getX();
                playerY = rect.getY();

            }

        }

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerX, playerY);
        playerBody = world.createBody(bodyDef);

        shape.setRadius(8);
        fixtureDef.shape = shape;
        playerBody.createFixture(fixtureDef);
        System.out.println(playerX + "   " + playerY);
    }


}
