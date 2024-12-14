package com.Code.Entity;

import com.Code.Effect.DamageArea;
import com.Code.Entity.Component.*;
import com.Code.Entity.System.*;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class ECSEngine extends PooledEngine {

    public Entity playerEntity = new Entity();

    public static final ComponentMapper<PlayerComponent> playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<Box2DComponent> box2DComponentMapper = ComponentMapper.getFor(Box2DComponent.class);
    public static final ComponentMapper<EnemyComponent> enemyComponentMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<DamageAreaComponent> damageAreaComponentMapper = ComponentMapper.getFor(DamageAreaComponent.class);
    public static final ComponentMapper<EntityComponent> entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
    public static final ComponentMapper<BossComponent> bossComponentMapper = ComponentMapper.getFor(BossComponent.class);

    public World world;
    Main game;
    public Array<Entity> EntityQueue = new Array<Entity>();

    public ECSEngine(Main game){
        super();
        this.game = game;
        world = game.world;

        this.addSystem(new PlayerAttackSystem(game));
        this.addSystem(new PlayerMovementSystem(game));
        this.addSystem(new EnemyMovementSystem(game));
        this.addSystem(new DamageAreaSystem(game));
        this.addSystem(new BossMovementSystem(game));
        this.addSystem(new BossAttackSystem(game));
    }

    public void createPlayer(Vector2 location){
        final Entity player = this.createEntity();
        Main.resetBox2D();
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 1;
        BodyDef bodyDef = Main.bodyDef;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location);
        box2DComponent.body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(game.BaseSize);
        FixtureDef fixtureDef = Main.fixtureDef;
        fixtureDef.shape = circleShape;
        box2DComponent.body.createFixture(fixtureDef).setUserData(player);
        box2DComponent.isDead = false;

        player.add(box2DComponent);

        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.direction = DirectionType.DOWN;
        playerComponent.speed = 150 * Main.PPM;
        playerComponent.timeAttack = 0;
        playerComponent.life = 50;
        playerComponent.isAttacking1 = false;
        playerComponent.isAttacking2 = false;
        playerComponent.maxLife = 50;
        playerComponent.startPosition = location;
        player.add(playerComponent);

        this.addEntity(player);
        playerEntity = player;
    }

    public void createEnemy(Vector2 location){
        final Entity enemy = this.createEntity();
        Main.resetBox2D();
        Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 2;
        BodyDef bodyDef = Main.bodyDef;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location);
        box2DComponent.body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(game.BaseSize);
        FixtureDef fixtureDef = Main.fixtureDef;
        fixtureDef.shape = circleShape;
        box2DComponent.body.createFixture(fixtureDef).setUserData(enemy);
        box2DComponent.isDead = false;
        enemy.add(box2DComponent);

        EnemyComponent enemyComponent = this.createComponent(EnemyComponent.class);
        enemyComponent.speed = 25 * Main.PPM;
        enemyComponent.life = 25;
        enemyComponent.startPosition = location;
        enemy.add(enemyComponent);

        this.addEntity(enemy);
    }

    public void createDamageArea(DamageArea damageArea){
        PlayerComponent playerComponent = playerComponentMapper.get(playerEntity);
        final Entity damageAreaEntity = this.createEntity();

        DamageAreaComponent damageAreaComponent = this.createComponent(DamageAreaComponent.class);
        damageAreaComponent.damage = damageArea.damage;
        damageAreaComponent.speed = damageArea.speed;
        damageAreaComponent.direction = damageArea.direction;
        damageAreaComponent.isAttack = damageArea.isAttack;
        damageAreaComponent.time = damageArea.time;
        damageAreaComponent.position = damageArea.position;
        damageAreaComponent.owner = damageArea.owner;
        damageAreaComponent.type = damageArea.type;

        damageAreaEntity.add(damageAreaComponent);

        Main.resetBox2D();
        Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 3;
        BodyDef bodyDef = Main.bodyDef;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(damageArea.position);
        box2DComponent.body = world.createBody(bodyDef);
        FixtureDef fixtureDef = Main.fixtureDef;
        if(damageAreaComponent.type == 1) {
            PolygonShape polygonShape = new PolygonShape();
            if(playerComponent.direction == DirectionType.LEFT || playerComponent.direction == DirectionType.RIGHT)
                polygonShape.setAsBox(14 * Main.PPM,32 * Main.PPM);
            else
                polygonShape.setAsBox(32 * Main.PPM,14 * Main.PPM);
            fixtureDef.shape = polygonShape;
        } else {
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(damageArea.width);
            fixtureDef.shape = circleShape;
        }

        fixtureDef.isSensor = true;
        box2DComponent.body.createFixture(fixtureDef).setUserData(damageAreaEntity);
        box2DComponent.isDead = false;
        damageAreaEntity.add(box2DComponent);

        this.addEntity(damageAreaEntity);
    }

    public void CreateBoss(Vector2 location){
        final Entity boss = this.createEntity();
        Main.resetBox2D();
        Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.ID = 4;
        BodyDef bodyDef = Main.bodyDef;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(location);
        box2DComponent.body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(game.BaseSize * 3f);
        FixtureDef fixtureDef = Main.fixtureDef;
        fixtureDef.shape = circleShape;
        box2DComponent.body.createFixture(fixtureDef).setUserData(boss);
        box2DComponent.isDead = false;
        boss.add(box2DComponent);

        BossComponent bossComponent = this.createComponent(BossComponent.class);
        bossComponent.speed = 25 * Main.PPM;

        bossComponent.life = 300;
        bossComponent.maxLife = 300;

        bossComponent.startPosition = location;
        bossComponent.readytoAttack = false;
        bossComponent.reloadtime = 0;
        bossComponent.Skill1 = true;
        bossComponent.Skill2 = false;
        bossComponent.cntSkill1 = 0;
        bossComponent.timeCntSkill1 = 0;
        bossComponent.timeSkill1 = 0;
        bossComponent.timeSkill2 = 0;
        bossComponent.stop = false;
        bossComponent.damed = false;
        bossComponent.direction = DirectionType.DOWN;
        boss.add(bossComponent);

        this.addEntity(boss);
    }

    public static void resetBox2D(){
        Main.bodyDef.type = BodyDef.BodyType.StaticBody;
        Main.bodyDef.fixedRotation = false;
        Main.fixtureDef.isSensor = false;
        Main.fixtureDef.shape = null;
    }

    public void destroyBody(){
        if(!world.isLocked()) {
            for (Entity entity : EntityQueue) {
                this.removeEntity(entity);
            }
        }
        EntityQueue.clear();
    }

    public Main getGame() {
        return game;
    }

}
