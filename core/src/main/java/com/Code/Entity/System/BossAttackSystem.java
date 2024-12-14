package com.Code.Entity.System;

import com.Code.Effect.DamageArea;
import com.Code.Entity.Component.BossComponent;
import com.Code.Entity.Component.Box2DComponent;
import com.Code.Entity.ECSEngine;
import com.Code.Main;
import com.Code.Others.DirectionType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import java.util.Objects;

import static java.lang.Math.abs;

public class BossAttackSystem extends IteratingSystem {

    Main game;
    Vector2 position;
    Vector2 direction;
    float[][] dirx = {{-1, -0.7f, 0, 0.7f, 1, 0.7f, 0, -0.7f},{-0.92f, -0.38f, 0.38f, 0.92f, 0.92f, 0.38f, -0.38f, -0.92f}};
    float[][] diry = {{0, 0.7f, 1, 0.7f, 0, -0.7f, -1, -0.7f},{0.38f, 0.92f, 0.92f, 0.38f, -0.38f, -0.92f, -0.92f, -0.38f}};

    float[] dir = {-1, -0.4f, 0 , 0.4f, 1};
    public BossAttackSystem(Main  game) {
        super(Family.all(BossComponent.class, Box2DComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BossComponent bossComponent = ECSEngine.bossComponentMapper.get(entity);
        Box2DComponent box2DComponent = ECSEngine.box2DComponentMapper.get(entity);
        Box2DComponent player = ECSEngine.box2DComponentMapper.get(game.ecsEngine.playerEntity);

        position = box2DComponent.body.getPosition();
        bossComponent.reloadtime += deltaTime;
        bossComponent.reloadSkill += deltaTime;
        if(bossComponent.reloadtime > 2f && bossComponent.readytoAttack && !bossComponent.stop){
            bossComponent.reloadtime = 0;
            for (int i = 0; i <= 7; i++){
                direction = new Vector2(dirx[0][i], diry[0][i]);
                game.ecsEngine.createDamageArea(new DamageArea(position, direction,
                    game.BaseSize/2, game.BaseSize/2, 10, 170 * Main.PPM, 5f,4,3, true));
            }
        }
        if(bossComponent.reloadSkill > 7f && bossComponent.readytoAttack){
            bossComponent.stop = true;
        }

        if(bossComponent.stop && bossComponent.readytoAttack){
            if(bossComponent.Skill1){
                bossComponent.timeSkill1 += deltaTime;
                bossComponent.timeCntSkill1 += deltaTime;
                if(bossComponent.timeCntSkill1 >= 0.6f){
                    bossComponent.cntSkill1 = (bossComponent.cntSkill1 + 1) % 2;
                    int t = bossComponent.cntSkill1;
                    for (int i = 0; i <= 7; i++){
                        direction = new Vector2(dirx[t][i], diry[t][i]);
                        game.ecsEngine.createDamageArea(new DamageArea(position, direction,
                            game.BaseSize/2, game.BaseSize/2, 15, 170 * Main.PPM, 5f, 4, 3, true));
                    }
                    bossComponent.timeCntSkill1 = 0;
                }

                if(bossComponent.timeSkill1 > 2.5f){

                    bossComponent.stop = false;
                    bossComponent.Skill1 = false;
                    bossComponent.Skill2 = true;
                    bossComponent.reloadtime = 0;
                    bossComponent.timeCntSkill1 = 0;
                    bossComponent.cntSkill1 = 0;
                    bossComponent.reloadSkill = 0;
                    bossComponent.timeSkill1 = 0;
                }
            }
            else{
                bossComponent.timeSkill1 += deltaTime;
                bossComponent.timeCntSkill1 += deltaTime;
                if(bossComponent.timeCntSkill1 >= 0.6f){

                    Vector2 dirCheck = new Vector2();
                    dirCheck.x = player.body.getPosition().x - box2DComponent.body.getPosition().x ;
                    dirCheck.y = player.body.getPosition().y - box2DComponent.body.getPosition().y;
                    if(!Objects.equals(dirCheck, new Vector2(0, 0))) {
                        if (abs(dirCheck.x) - abs(dirCheck.y) > 0) {
                            if (dirCheck.x < 0) bossComponent.direction = DirectionType.LEFT;
                            else bossComponent.direction = DirectionType.RIGHT;
                        } else {
                            if (dirCheck.y < 0) bossComponent.direction = DirectionType.DOWN;
                            else bossComponent.direction = DirectionType.UP;
                        }
                    }
                    for (int i = 0; i < 5; i++){
                        switch (bossComponent.direction){
                            case RIGHT:{
                                direction =(new Vector2(1f, dir[i])) ;
                                break;
                            }
                            case LEFT:{
                                direction =(new Vector2(-1f, dir[i])) ;
                                break;

                            }
                            case UP:{
                                direction = (new Vector2(dir[i], 1f)) ;
                                break;
                            }
                            case DOWN:{
                                direction =(new Vector2(dir[i], -1f));
                                break;
                            }
                        }

                        game.ecsEngine.createDamageArea(new DamageArea(position, direction,
                            game.BaseSize/2, game.BaseSize/2, 15, 190 * Main.PPM, 5f, 4,3, true));
                    }
                    bossComponent.timeCntSkill1 = 0;
                }

                if(bossComponent.timeSkill1 > 3f){

                    bossComponent.stop = false;
                    bossComponent.Skill1 = true;
                    bossComponent.Skill2 = false;
                    bossComponent.reloadtime = 0;
                    bossComponent.timeCntSkill1 = 0;
                    bossComponent.cntSkill1 = 0;
                    bossComponent.reloadSkill = 0;
                    bossComponent.timeSkill1 = 0;
                }

            }
        }
    }

}
