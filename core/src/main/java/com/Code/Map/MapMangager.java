package com.Code.Map;


import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.Code.Main.*;

public class MapMangager {

    public Maploader currentMap;
    MapType currentMapType, nextMapType;
    Main game;
    World world;
    public ECSEngine ecsEngine;
    private Array<Entity> entityToRemove;
    private Array<Body> bodies;




    public MapMangager(Main game){
        this.game = game;
        world = game.world;

        ecsEngine = game.ecsEngine;
        currentMapType = MapType.STARTING;
        nextMapType = MapType.STARTING;
        entityToRemove = new Array<Entity>();
        bodies = new Array<Body>();

    }

    public void setMap(){
        if(currentMapType == nextMapType && currentMap != null) return;
        destroyMap();
        currentMapType = nextMapType;
        currentMap = new Maploader(currentMapType);
        currentMap.CreateMap();


        spawnPlayer();
        spawnEnemy();
        spawnBoss();
        spawnCollisionArea();

    }

    public void spawnPlayer(){
        ecsEngine.createPlayer(currentMap.getPlayerStartingPosition());
    }

    public void spawnEnemy(){
        for(Vector2 location : currentMap.getEnemyPosition()) {
            ecsEngine.createEnemy(location);
        }
    }

    public void spawnBoss(){
        for(Vector2 location : currentMap.getBossPosition()) {
            ecsEngine.CreateBoss(location);
        }
    }

    public void spawnCollisionArea(){
        for (final CollisionArea collisionArea: currentMap.getCollisionAreas()) {

            resetBox2D();
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyDef);

            ChainShape cShape = new ChainShape();
            cShape.createLoop(collisionArea.getVertices());
            fixtureDef.shape = cShape;
            body.createFixture(fixtureDef);
            cShape.dispose();
            body.setUserData("GROUND");

        }
    }

    public void destroyMap() {
        // clean map entities and body
        world.getBodies(bodies);
        destroyCollisionArea();
        destroyObjects();
    }

    private void destroyCollisionArea() {
        for (final Body body: bodies) {
            if ("GROUND".equals(body.getUserData())) {
                world.destroyBody(body);
            }
        }
    }

    private void destroyObjects() {
        for(final Entity entity: ecsEngine.getEntities()) {
            if (ECSEngine.playerComponentMapper.get(entity) == null) {
                entityToRemove.add(entity);
            }
        }
        for (final Entity entity: entityToRemove) {
            ecsEngine.removeEntity(entity);
        }
        entityToRemove.clear();
    }

}
