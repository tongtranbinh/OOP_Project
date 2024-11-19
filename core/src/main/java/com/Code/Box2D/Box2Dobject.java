package com.Code.Box2D;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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

    public InteractTileObject CollisionObject = new InteractTileObject();
    public void CreateCollision(){

        MapLayer collisionsLayer = map.getLayers().get("Collision");
        MapObjects collisions = collisionsLayer.getObjects();
        for(MapObject collision : collisions){
            if(collision instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) collision).getRectangle();

                CollisionObject.bodyDef.type = BodyDef.BodyType.StaticBody;
                CollisionObject.bodyDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

                CollisionObject.body = world.createBody(CollisionObject.bodyDef);

                CollisionObject.polygonShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                CollisionObject.fixtureDef.shape = CollisionObject.polygonShape;

                CollisionObject.body.createFixture(CollisionObject.fixtureDef);
            }
            if(collision instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) collision).getPolygon();

                //lay dinh tu polygon
                float verticles[] = polygon.getTransformedVertices();

                ChainShape cShape = new ChainShape();
                cShape.createLoop(verticles);

                CollisionObject.bodyDef.type = BodyDef.BodyType.StaticBody;
                CollisionObject.bodyDef.position.set(polygon.getOriginX(), polygon.getOriginY());
                CollisionObject.body = world.createBody(CollisionObject.bodyDef);

                CollisionObject.fixtureDef.shape = cShape;
                CollisionObject.body.createFixture(CollisionObject.fixtureDef);

                cShape.dispose();
                //System.out.println(polygon.getOriginX() + "   " + polygon.getOriginY());
            }

            CollisionObject.body.setUserData("Collision");

        }
    }

    public InteractTileObject playerObject = new InteractTileObject();
    public void CreatePlayer(){


        MapLayer pointLayer = map.getLayers().get("Player");
        MapObjects box2Dplayers = pointLayer.getObjects();

        for(MapObject box2Dplayer : box2Dplayers){

            if(box2Dplayer instanceof RectangleMapObject){
                RectangleMapObject obj = ((RectangleMapObject) box2Dplayer);
                Rectangle rect = obj.getRectangle();
                playerObject.objectX = rect.getX();
                playerObject.objectY = rect.getY();

            }

        }

        playerObject.bodyDef.type = BodyDef.BodyType.DynamicBody;
        playerObject.bodyDef.position.set(playerObject.objectX, playerObject.objectY);
        playerObject.body = world.createBody(playerObject.bodyDef);

        playerObject.circleShape.setRadius(8);
        playerObject.fixtureDef.shape = playerObject.circleShape;
        playerObject.body.createFixture(playerObject.fixtureDef);

        playerObject.fixtureDef.isSensor = true;


        playerObject.body.setUserData("Player");
    }

}
