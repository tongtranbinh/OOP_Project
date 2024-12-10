package com.Code.Entity;

import com.Code.Effect.DamageArea;
import com.Code.Entity.Component.*;
import com.Code.Entity.System.*;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


import static com.Code.Main.*;
import static com.Code.Main.MAX_STEP_TIME;
import static com.Code.Others.DirectionType.DOWN;


public class ECSEngine extends PooledEngine {




    public Entity playerEntity = new Entity();

    public static final ComponentMapper<PlayerComponent> playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<Box2DComponent> box2DComponentMapper = ComponentMapper.getFor(Box2DComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyComponentMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<DamageAreaComponent> damageAreaComponentMapper = ComponentMapper.getFor(DamageAreaComponent.class);
    public static final ComponentMapper<EntityComponent> entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);


    public World world;
    Main game;
    public Array<Entity> EntityQueue = new Array<>();


    public ECSEngine(Main game){
        super();
        this.game = game;
        world = game.world;


        this.addSystem(new PlayerAttackSystem(game));
        this.addSystem(new PlayerMovementSystem(game));
        this.addSystem(new EnemyMovementSystem(game));
        this.addSystem(new DamageAreaSystem(game));
        this.addSystem(new PhysicDebugSystem(game));
    }
    public void createPlayer(Vector2 location){
        final Entity player = this.createEntity();

        //box2D component
        resetBox2D();
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location);
        box2DComponent.body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(game.BaseSize, game.BaseSize);
        fixtureDef.shape = polygonShape;
        box2DComponent.body.createFixture(fixtureDef).setUserData(player);
        box2DComponent.isDead = false;

        player.add(box2DComponent);

        //playerComponent
        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.direction = DOWN;
        playerComponent.speed = 150 * Main.PPM;
        playerComponent.timeAttack = 0;
        playerComponent.life = 10;
        playerComponent.startPosition = location;
        player.add(playerComponent);

        this.addEntity(player);
        playerEntity = player;

    }

    public void createEnemy(Vector2 location){
        final Entity enemy = this.createEntity();
        //box2D component
        resetBox2D();
        Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 2;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location);
        box2DComponent.body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(game.BaseSize, game.BaseSize);
        fixtureDef.shape = polygonShape;
        box2DComponent.body.createFixture(fixtureDef).setUserData(enemy);
        box2DComponent.isDead = false;
        enemy.add(box2DComponent);

        //enemyComponent
        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.speed = 25 * Main.PPM;
        enemyComponent.life = 3;
        enemyComponent.startPosition = location;
        enemy.add(enemyComponent);

        this.addEntity(enemy);


    }

    public void createDamageArea(DamageArea damageArea){
        final Entity damageAreaEntity = this.createEntity();
        //damage area component

        DamageAreaComponent damageAreaComponent = this.createComponent(DamageAreaComponent.class);
        damageAreaComponent.damage = damageArea.damage;
        damageAreaComponent.speed = damageArea.speed;
        damageAreaComponent.direction = damageArea.direction;
        damageAreaComponent.isAttack = damageArea.isAttack;
        damageAreaComponent.time = damageArea.time;
        damageAreaComponent.position = damageArea.position;

        damageAreaEntity.add(damageAreaComponent);

        //box2D component
        resetBox2D();
        Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 3;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(damageArea.position);
        box2DComponent.body = world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(damageArea.width, damageArea.height);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;

        box2DComponent.body.createFixture(fixtureDef).setUserData(damageAreaEntity);
        box2DComponent.isDead = false;

        damageAreaEntity.add(box2DComponent);

        this.addEntity(damageAreaEntity);

    }




    public static void resetBox2D(){
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.isSensor = false;
        fixtureDef.shape = null;
    }

    public void destroyBody(){
        if(!world.isLocked()) {
            for (Entity entity : EntityQueue) {
                System.out.println("xoa  " + entity);
                this.removeEntity(entity);
            }
        }

        EntityQueue.clear();
    }
}