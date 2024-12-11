package com.Code.Map;

import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Objects;

public class Maploader {
    public TmxMapLoader tmxMapLoader;
    public TiledMap tiledMap;
    MapType currentMapType;



    public Vector2 PlayerStartingPosition = new Vector2();
    public Array<CollisionArea> collisionAreas = new Array<CollisionArea>();
    public Array<Vector2> enemyPosition = new Array<Vector2>();
    public Array<Vector2> bossPosition = new Array<Vector2>();

    public Maploader(MapType mapType)
    {
        currentMapType = mapType;
        tmxMapLoader = new TmxMapLoader();
    }
    public void CreateMap(){
        tiledMap = tmxMapLoader.load(currentMapType.getFilepath());
        getPlayerObject();
        getEnemyObject();
        getCollisionObject();
        getBossObject();
    }


    public void getPlayerObject(){

        MapLayer mapLayer = tiledMap.getLayers().get("Player");
        if(mapLayer == null) return;

        MapObjects gameObjects = mapLayer.getObjects();

        for(MapObject object : gameObjects){
            if (object instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
                Rectangle rectangle = rectangleMapObject.getRectangle();
                PlayerStartingPosition = new Vector2(rectangle.x * Main.PPM, rectangle.y * Main.PPM);
            }
        }
    }

    public void getCollisionObject(){

        MapLayer mapLayer = tiledMap.getLayers().get("Collision");
        if(mapLayer == null) return;
        MapObjects gameObjects = mapLayer.getObjects();


		for (final MapObject mapObj: gameObjects) {
        if (mapObj instanceof RectangleMapObject) {
            final RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObj;
            final Rectangle rectangle = rectangleMapObject.getRectangle();
            float[] rectVertices = new float[8];

            rectVertices[0] = 0;
            rectVertices[1] = 0;

            rectVertices[2] = 0;
            rectVertices[3] = rectangle.height;

            rectVertices[4] = rectangle.width;
            rectVertices[5] = rectangle.height;

            rectVertices[6] = rectangle.width;
            rectVertices[7] = 0;

//            rectVertices[8] = 0;
//            rectVertices[9] = 0;

            //System.out.println(rectangle.getX() + "cc" + rectangle.getY());
            if(rectangle.width == 0 || rectangle.height == 0) {continue;}
            collisionAreas.add(new CollisionArea(rectangle.getX(), rectangle.getY(), rectVertices));
        }
        else if (mapObj instanceof PolygonMapObject) {
            final PolygonMapObject polygonMapObject = (PolygonMapObject) mapObj;
            final Polygon polygon = polygonMapObject.getPolygon();
            collisionAreas.add(new CollisionArea(polygon.getX(),polygon.getY(),polygon.getVertices()));
            }
        }
    }

    public void getEnemyObject(){
        MapLayer mapLayer = tiledMap.getLayers().get("Enemy");
        if(mapLayer == null) return;

        MapObjects gameObjects = mapLayer.getObjects();

        for(MapObject object : gameObjects){
            if (object instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
                Rectangle rectangle = rectangleMapObject.getRectangle();
                enemyPosition.add(new Vector2(rectangle.x * Main.PPM, rectangle.y * Main.PPM));
            }
        }
    }

    public void getBossObject(){

        MapLayer mapLayer = tiledMap.getLayers().get("Boss");
        if(mapLayer == null) return;

        MapObjects gameObjects = mapLayer.getObjects();

        for(MapObject object : gameObjects){
            if (object instanceof RectangleMapObject) {
                final RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
                Rectangle rectangle = rectangleMapObject.getRectangle();
                bossPosition.add(new Vector2(rectangle.x * Main.PPM, rectangle.y * Main.PPM));
            }
        }
    }



    public Vector2 getPlayerStartingPosition(){return PlayerStartingPosition;}

    public Array<CollisionArea> getCollisionAreas(){return collisionAreas;}

    public Array<Vector2> getEnemyPosition(){return enemyPosition;}

    public Array<Vector2> getBossPosition(){return bossPosition;}



}
