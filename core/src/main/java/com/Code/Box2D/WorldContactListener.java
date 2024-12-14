package com.Code.Box2D;

import com.Code.Entity.Component.*;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {

    Main game;
    private boolean hasPlayer;
    private boolean hasGameObj;
    private boolean hasWeapon;
    private boolean hasEnemy;
    private boolean hasItem;
    private boolean hasDoor;
    private boolean hasBoss;
    private boolean hasGround;
    private boolean hasDamageArea;

    public WorldContactListener(Main game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        reset();
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity entity1 = (Entity) fixtureA.getUserData();
        Entity entity2 = (Entity) fixtureB.getUserData();

        Box2DComponent box1 = new Box2DComponent();
        Box2DComponent box2 = new Box2DComponent();
        if(entity1 != null) box1 = ECSEngine.box2DComponentMapper.get(entity1);
        if(entity2 != null) box2 = ECSEngine.box2DComponentMapper.get(entity2);

        switch (box1.ID) {
            case 0: hasGround = true; break;
            case 1: hasPlayer = true; break;
            case 2: hasEnemy = true; break;
            case 3: hasDamageArea = true; break;
            case 4: hasBoss = true; break;
        }

        switch (box2.ID) {
            case 0: hasGround = true; break;
            case 1: hasPlayer = true; break;
            case 2: hasEnemy = true; break;
            case 3: hasDamageArea = true; break;
            case 4: hasBoss = true; break;
        }

        if(hasPlayer && hasEnemy) {
            Entity playerEntity;
            Entity enemyEntity;
            if(box1.ID == 1) { playerEntity = entity1; enemyEntity = entity2; }
            else { playerEntity = entity2; enemyEntity = entity1; }

            playerVSenemy(playerEntity, enemyEntity);
        }
        if(hasDamageArea && hasEnemy) {
            Entity damageAreaEntity;
            Entity enemyEntity;
            if(box1.ID == 3) { damageAreaEntity = entity1; enemyEntity = entity2; }
            else { damageAreaEntity = entity2; enemyEntity = entity1; }
            damageAreaVSenemy(damageAreaEntity, enemyEntity);
        }
        if(hasDamageArea && hasPlayer){
            Entity damageAreaEntity;
            Entity playerEntity;
            if(box1.ID == 3) { damageAreaEntity = entity1; playerEntity = entity2; }
            else { damageAreaEntity = entity2; playerEntity = entity1; }
            damageAreaVSplayer(damageAreaEntity, playerEntity);
        }
        if(hasBoss && hasDamageArea){
            Entity damageAreaEntity;
            Entity bossEntity;
            if(box1.ID == 3) { damageAreaEntity = entity1; bossEntity = entity2; }
            else { damageAreaEntity = entity2; bossEntity = entity1; }

            damageAreaVSboss(damageAreaEntity, bossEntity);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }


    public void playerVSenemy(Entity player, Entity enemy){
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(player);
        EnemyComponent enemyComponent = ECSEngine.enemyComponentMapper.get(enemy);
        playerComponent.life -= 5;
    }

    public void damageAreaVSenemy(Entity damageArea, Entity enemy){
        EnemyComponent enemyComponent = ECSEngine.enemyComponentMapper.get(enemy);
        DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(damageArea);
        if(damageAreaComponent.owner != 1) return;
        game.ecsEngine.EntityQueue.add(damageArea);
        enemyComponent.life -= damageAreaComponent.damage;
        if(enemyComponent.life <= 0) {
            game.ecsEngine.EntityQueue.add(enemy);
        }
    }

    public void damageAreaVSplayer(Entity damageArea, Entity player){
        DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(damageArea);
        PlayerComponent playerComponent = ECSEngine.playerComponentMapper.get(player);
        if(damageAreaComponent.owner == 1) return;
        game.ecsEngine.EntityQueue.add(damageArea);
        playerComponent.life -= damageAreaComponent.damage;
    }

    public void damageAreaVSboss(Entity damageArea, Entity boss){
        DamageAreaComponent damageAreaComponent = ECSEngine.damageAreaComponentMapper.get(damageArea);
        BossComponent bossComponent = ECSEngine.bossComponentMapper.get(boss);
        if(damageAreaComponent.owner == 4) return;
        game.ecsEngine.EntityQueue.add(damageArea);

        // Trừ máu boss bằng currentLife
        bossComponent.currentLife -= damageAreaComponent.damage;
        if (bossComponent.currentLife < 0) bossComponent.currentLife = 0;
        bossComponent.damed = true;

        // Cập nhật HUD
        if (game.hud != null) {
            game.hud.updateBossHealth(bossComponent.currentLife);
            game.hud.showBossHealth();
        }
    }

    void reset() {
        hasPlayer = false;
        hasGameObj = false;
        hasItem = false;
        hasEnemy = false;
        hasWeapon = false;
        hasDoor = false;
        hasBoss = false;
        hasDamageArea = false;
        hasGround = false;
    }

}
