package com.Code.Map;


import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.Code.Main.*;

public class MapMangager {

    public Maploader currentMap;
    Main game;
    World world;
    public ECSEngine ecsEngine;



    public MapMangager(Main game){
        this.game = game;
        world = game.world;

        ecsEngine = game.ecsEngine;
        currentMap = new Maploader(game);

        spawnPlayer();
        spawnEnemy();
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
            //body.setUserData("GROUND");
            cShape.dispose();
        }
    }

}
